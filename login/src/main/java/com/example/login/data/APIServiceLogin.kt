package com.example.login.data

import retrofit2.http.Body
import retrofit2.http.POST

interface APIServiceLogin {

    @POST("login")
    suspend fun login(@Body userServerModel: UserServerModel ): ServerLoginResponseModel
}