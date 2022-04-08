package com.example.mycontactlist.database

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject

class ContactRespository(private val contactDao: ContactDao) {
    val allContact: LiveData<List<Contact>> = contactDao.getAllContact()

    @WorkerThread
    suspend fun add(contact: Contact){
        contactDao.insert(contact)
    }

    @WorkerThread
    suspend fun update(contact: Contact){
        contactDao.update(contact)
    }

    @WorkerThread
    suspend fun delete(contact: Contact){
        contactDao.delete(contact)
    }

    fun addWebContact(context: Context, contact: Contact){
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            "https://seekt.000webhostapp.com/api/user/create.php?name=" + contact.name + "&contact=" + contact.phone,
            null,
            { response ->
                try{
                    if(response != null){
                        val strResponse = response.toString()
                        val jsonResponse = JSONObject(strResponse)
                        val success = jsonResponse.get("success")
                        if(success.equals("1")){
                            Toast.makeText(context, "Record saved", Toast.LENGTH_SHORT).show()
                        }
                        else{
                            Toast.makeText(context, "Record not saved", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                catch(e: Exception){
                    Log.d("Exception", e.message.toString())
                }
            },
            {
                error ->
                    Log.d("addWebContact", error.message.toString())
            }
        )
    }
}