package com.example.login.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ServerLoginResponseModel(
    @SerializedName("token")
    @Expose
    val token : String,
    @SerializedName("error")
    @Expose
    val error : String
)