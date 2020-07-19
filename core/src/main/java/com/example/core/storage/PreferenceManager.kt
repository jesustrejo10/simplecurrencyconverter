package com.example.core.storage

import android.content.Context
import android.content.SharedPreferences

const val USER_LOGGED_IN = "USER_LOGGED_IN"

class PreferenceManager(private val context: Context) {
    val preferenceManager : SharedPreferences

    init {
        val fileName = "key_sharedp"
        preferenceManager = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
    }

    fun checkIfUserAlreadyLoggedIn(): Boolean {

        return preferenceManager.getBoolean(USER_LOGGED_IN,false)


    }

}