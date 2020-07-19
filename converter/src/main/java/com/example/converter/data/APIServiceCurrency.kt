package com.example.converter.data

import retrofit2.http.GET

interface APIServiceCurrency {


    @GET
    fun test (): Boolean
}