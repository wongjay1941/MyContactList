package com.example.mycontactlist

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.mycontactlist.database.Contact
import com.example.mycontactlist.database.ContactAdapter
import com.example.mycontactlist.database.ContactViewModel
import com.example.mycontactlist.databinding.FragmentFirstBinding
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val myViewModel: ContactViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Enable Option Menu
        setHasOptionsMenu(true)

        //Define an instance of adapter
        val contactAdapter = ContactAdapter()

        //--------------Original way
        //myViewModel.contactList.observe(    //Observe the LiveData (contactList) for changes
        //    viewLifecycleOwner,
        //     {
        //         contactAdapter.setContact(it)  //it = contactList
        //     }
        //)

        //--------------Changed way
        myViewModel.contactList.observe(    //Observe the LiveData (contactList) for changes
            viewLifecycleOwner
        ) {
            contactAdapter.setContact(it)  //it = contactList
        }

        //No need to set manually with the Observer
        //myViewModel.contactList // access to the contact list
        //contactAdapter.setContact(myViewModel.contactList) //Pass contact records to the adapter

        //Assign the adapter to RecyclerView
        binding.listViewContact.adapter = contactAdapter

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.action_save).isVisible = false
        menu.findItem(R.id.action_download).isVisible = true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_download -> {
                //Download contact records from a cloud server
                downloadContact()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun downloadContact() {
        val requestQueue = Volley.newRequestQueue(context)
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            "https://seekt.000webhostapp.com/api/user/read.php",
            null,
            { response ->
                try{
                    if(response != null){
                        val strResponse = response.toString()
                        val jsonResponse = JSONObject(strResponse)
                        val jsonArray: JSONArray = jsonResponse.getJSONArray("records")
                        val size: Int = jsonArray.length()
                        for(i in 0..size - 1){
                            val jsonContact: JSONObject = jsonArray.getJSONObject(i)
                            val contact = Contact(jsonContact.getString("name"), jsonContact.getString("contact"))
                            myViewModel.addContact(contact)
                        }
                    }
                }catch(e: Exception){
                    Toast.makeText(context, "Exception: " + e.message, Toast.LENGTH_SHORT).show()
                    Log.d("Exception: ", e.message.toString())
                }
            },
            { error ->
                    Log.d("First Fragment", error.message.toString())
            }
        )
        requestQueue.add(jsonObjectRequest)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}