package com.example.converter.data

class GetCurrenciesRepository (private val apiHelper : LoginCurrencyHelper){

    suspend fun login() = apiHelper.getCurrencies()
}