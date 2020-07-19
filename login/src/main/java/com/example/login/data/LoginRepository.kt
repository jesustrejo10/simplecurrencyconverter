package com.example.login.data

class LoginRepository (private val apiHelper : LoginApiHelper){

    suspend fun login(userServerModel: UserServerModel) = apiHelper.login(userServerModel)
}