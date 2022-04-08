package com.example.mycontactlist

import android.app.DownloadManager
import android.content.Context
import androidx.core.content.contentValuesOf
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class MySingleton constructor(context: Context){
    companion object{
        @Volatile
        private var INSTANCE: MySingleton? = null

        fun getInstance(context: Context): MySingleton {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: MySingleton(context).also {
                    INSTANCE = it
                }
            }
        }
    }

    private val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context)
    }

    fun<T> addToRequestQueue(req: Request<T>){
        requestQueue.add(req)
    }
}