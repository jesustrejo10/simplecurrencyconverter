package com.example.converter.data

class CurrencyHelper(val apiServiceCurrency: APIServiceCurrency) {

    suspend fun getCurrencies() = apiServiceCurrency.test()

}