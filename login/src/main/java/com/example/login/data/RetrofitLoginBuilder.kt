package com.example.login.data

import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitLoginBuilder  {

    /**
     * Dummy API used to login
     */
    private const val BASE_URL = "https://reqres.in/api/"

    private fun getRetrofit(): Retrofit {
        val clientBuilder = OkHttpClient.Builder()
        clientBuilder.addNetworkInterceptor(StethoInterceptor())

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(clientBuilder.build())
            .build() //Doesn't require the adapter
    }

    val apiService: APIServiceLogin = getRetrofit().create(APIServiceLogin::class.java)
}