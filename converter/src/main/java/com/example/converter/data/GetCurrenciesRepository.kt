package com.example.converter.data

class GetCurrenciesRepository (private val apiHelper : CurrencyHelper){

    suspend fun getCurrencies() = apiHelper.getCurrencies()
}