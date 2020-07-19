package com.example.converter.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Cripto(

    @SerializedName("name")
    @Expose
    val name : String,

    @SerializedName("symbol")
    @Expose
    val symbol : String,

    @SerializedName("quote")
    @Expose
    val quote : Quote

    )