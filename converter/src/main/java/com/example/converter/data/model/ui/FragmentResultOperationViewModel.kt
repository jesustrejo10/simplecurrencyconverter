package com.example.converter.data.model.ui

import androidx.lifecycle.ViewModel
import com.example.converter.data.model.ReportData
import com.google.gson.Gson
import java.lang.Exception

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
}
