package com.example.converter.data

class LoginCurrencyHelper(val apiServiceCurrency: APIServiceCurrency) {

    suspend fun getCurrencies() = apiServiceCurrency.test()

}