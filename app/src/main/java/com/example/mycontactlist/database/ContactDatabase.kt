package com.example.mycontactlist.database

import android.content.Context
import android.provider.CalendarContract
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Contact::class), version = 1, exportSchema = false)
abstract class ContactDatabase:RoomDatabase() {
    abstract fun contactDao(): ContactDao
    companion object{
        @Volatile
        private var INSTANCE: ContactDatabase? = null
        fun getDatabase(context: Context) : ContactDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null) {
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext, ContactDatabase::class.java, "contactDB").build()
                INSTANCE = instance
                return instance
            }
        }
    }
}