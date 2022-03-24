package com.example.mycontactlist.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ContactViewModel(application: Application):AndroidViewModel(application) {
    var contactList:LiveData<List<Contact>>
    private val repository:ContactRespository
    init {
        val contactDao = ContactDatabase.getDatabase(application).contactDao()
        repository = ContactRespository(contactDao)
        contactList = repository.allContact
    }

    fun addContact(contact: Contact) = viewModelScope.launch {
        repository.add(contact)
    }

    fun updateContact(contact: Contact) = viewModelScope.launch {
        repository.update(contact)
    }

    fun deleteContact(contact: Contact) = viewModelScope.launch {
        repository.delete(contact)
    }
}