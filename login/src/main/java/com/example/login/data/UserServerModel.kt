package com.example.login.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserServerModel (
    @SerializedName("email")
    @Expose
    val email : String,
    @SerializedName("password")
    @Expose
    val password : String
)