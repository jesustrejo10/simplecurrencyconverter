package com.example.converter.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Quote (
    @SerializedName("USD")
    @Expose
    val currency : Currency
)