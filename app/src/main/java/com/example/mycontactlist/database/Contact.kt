package com.example.mycontactlist.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact")
data class Contact (@PrimaryKey val phone: String, val name: String){
    override fun toString(): String {
        return "$name : $phone"
    }
}