package com.example.core.storage

import android.content.Context
import android.content.SharedPreferences

const val USER_LOGGED_IN = "USER_LOGGED_IN"
const val USER_TOKEN = "USER_TOKEN"
class PreferenceManager(private val context: Context) {
    private val preferenceManager : SharedPreferences

    init {
        val fileName = "key_sharedp"
        preferenceManager = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
    }

    fun checkIfUserAlreadyLoggedIn(): Boolean {
        return false
        //return !preferenceManager.getString(USER_TOKEN,"").isNullOrEmpty()
    }

    fun saveToken(token: String) {
        preferenceManager.edit().putString((USER_TOKEN), token).apply()
    }

    fun removeUserToken() {
        preferenceManager.edit().putString((USER_TOKEN), "").apply()
    }
}