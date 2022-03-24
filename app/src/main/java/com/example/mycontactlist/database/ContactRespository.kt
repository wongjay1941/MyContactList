package com.example.mycontactlist.database

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

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
}