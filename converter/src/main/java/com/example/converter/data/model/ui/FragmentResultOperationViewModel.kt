package com.example.converter.data.model.ui

import androidx.lifecycle.ViewModel
import com.example.converter.data.model.ReportData
import com.google.gson.Gson


class FragmentResultOperationViewModel : ViewModel() {

    lateinit var data : ReportData
    fun parseData(string: String?): ReportData? {

        return try{
            val gson = Gson()
            val reportData = gson.fromJson(string,ReportData::class.java)
            data = reportData
            data
        }catch (e : Exception){
            e.printStackTrace()
            null
        }
    }

    fun generateQR() {


    }

    fun generateQRData(): String? {
        val data = "Thank you for use simple currency converter." +
                "The entered value is: ${data.initialCurrency} ${data.initialValue}" +
                "Equivalent value in USD is ${data.valueInUsd} in base of ${data.exchangeRateInUsd}" +
                "Equivalent value in USD is ${data.valueInEur} in base of ${data.exchangeRateInEur}" +
                "Equivalent value in USD is ${data.valueInBs} in base of ${data.exchangeRateInBs}" +
                "Equivalent value in USD is ${data.valueInBtc} in base of ${data.exchangeRateInBtc}" +
                "Equivalent value in USD is ${data.valueInEth} in base of ${data.exchangeRateInEth}" +
                "Equivalent value in USD is ${data.valueInPtr} in base of ${data.exchangeRateInPtr}"

                return data
    }
}
