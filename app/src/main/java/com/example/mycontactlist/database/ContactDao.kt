package com.example.mycontactlist.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ContactDao {
    @Insert
    suspend fun insert(contact: Contact)

    @Delete
    suspend fun delete(contact: Contact)

    @Update
    suspend fun update(contact: Contact)

    @Query("SELECT * FROM contact ORDER BY name ASC")
    fun getAllContact(): LiveData<List<Contact>>
}