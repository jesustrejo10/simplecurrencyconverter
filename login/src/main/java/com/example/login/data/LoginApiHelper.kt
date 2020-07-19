package com.example.login.data

class LoginApiHelper(private val apiLoginService : APIServiceLogin) {

    suspend fun login(loginServerModel : UserServerModel) = apiLoginService.login(loginServerModel)

}