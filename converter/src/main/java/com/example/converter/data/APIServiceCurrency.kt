package com.example.converter.data

import com.example.converter.data.model.Cripto
import com.example.converter.data.model.CriptoResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface APIServiceCurrency {

    @Headers("X-CMC_PRO_API_KEY: 4959d3af-11aa-4a48-a653-395df8edde77")
    @GET("cryptocurrency/listings/latest")
    suspend fun test (): CriptoResponse
}
