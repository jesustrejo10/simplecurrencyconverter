package com.example.converter.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ReportData (
    @SerializedName("initialValue")
    @Expose
    val initialValue : String,
    @SerializedName("initialCurrency")
    @Expose
    val initialCurrency : String,

    @SerializedName("valueInUsd")
    @Expose
    val valueInUsd : String,
    @SerializedName("exchangeRateInUsd")
    @Expose
    val exchangeRateInUsd : String,

    @SerializedName("valueInEur")
    @Expose
    val valueInEur : String,
    @SerializedName("exchangeRateInEur")
    @Expose
    val exchangeRateInEur : String,

    @SerializedName("valueInBs")
    @Expose
    val valueInBs : String,
    @SerializedName("exchangeRateInBs")
    @Expose
    val exchangeRateInBs : String,

    @SerializedName("valueInBtc")
    @Expose
    val valueInBtc : String,
    @SerializedName("exchangeRateInBtc")
    @Expose
    val exchangeRateInBtc : String,

    @SerializedName("valueInEth")
    @Expose
    val valueInEth : String,
    @SerializedName("exchangeRateInEth")
    @Expose
    val exchangeRateInEth : String,

    @SerializedName("valueInPtr")
    @Expose
    val valueInPtr : String,
    @SerializedName("exchangeRateInPtr")
    @Expose
    val exchangeRateInPtr : String
)