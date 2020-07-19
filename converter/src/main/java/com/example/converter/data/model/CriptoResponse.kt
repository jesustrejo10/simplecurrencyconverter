package com.example.converter.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CriptoResponse (

    @SerializedName("data")
    @Expose
    val criptoList : List<Cripto>

)