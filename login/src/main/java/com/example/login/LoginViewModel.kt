package com.example.login

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.core.data.Resource
import com.example.core.storage.PreferenceManager
import com.example.login.data.LoginApiHelper
import com.example.login.data.RetrofitLoginBuilder
import com.example.login.data.ServerLoginResponseModel
import com.example.login.data.UserServerModel
import kotlinx.coroutines.*
import java.net.UnknownHostException

class LoginViewModel : ViewModel() {

    val uiStatusManager = MutableLiveData<UIValidator>()
    val operationResultManager = MutableLiveData<OperationResult>()
    lateinit var preferenceManager: PreferenceManager


    fun verifyScreen(email: String, password: String) {
        val validEmail =  !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        if(!validEmail) {
            uiStatusManager.postValue(UIValidator.INVALID_EMAIL)
            return
        }
        var validPassword = password.isNotEmpty()
        if(!validPassword)
            uiStatusManager.postValue(UIValidator.INVALID_PASSWORD)
        else{
            validPassword = password.length > 4
            if(!validPassword)
                uiStatusManager.postValue(UIValidator.INVALID_EMAIL_LENGHT)
        }
        if(validEmail && validPassword){
            val hardCodedEmail = "eve.holt@reqres.in"
            val hardCodedPassword = "cityslicka"
            val serverUserModel = UserServerModel(hardCodedEmail,hardCodedPassword)
            tryLogin(serverUserModel)
            operationResultManager.postValue(OperationResult(OperationResultStatus.LOGIN_REQUESTED,null))
        }

    }




    fun successLogin(data: ServerLoginResponseModel?) {
        if(data?.token.isNullOrEmpty())
            return
        preferenceManager.saveToken(data?.token!!)

        operationResultManager.postValue(OperationResult(OperationResultStatus.LOGIN_SUCCESS,null))

    }

    fun tryLogin(p0: UserServerModel) {

        CoroutineScope(Dispatchers.IO).launch {
            supervisorScope {
                try {
                    val call1 = async { LoginApiHelper(RetrofitLoginBuilder.apiService).login(p0) }
                    val apiResponse = call1.await()
                    successLogin(apiResponse)

                } catch (exception: Exception) {
                    if(exception is UnknownHostException){
                        operationResultManager.postValue( OperationResult(
                            OperationResultStatus.LOGIN_ERROR,"There is no internet connection available, please try again later."
                        ))
                    }else {
                        operationResultManager.postValue(
                            OperationResult(
                                OperationResultStatus.LOGIN_ERROR,
                                exception.message
                            )
                        )
                    }
                }
            }
        }
    }


    enum class UIValidator {
        INVALID_EMAIL, INVALID_PASSWORD,INVALID_EMAIL_LENGHT
    }

    enum class OperationResultStatus {
        LOGIN_REQUESTED,LOGIN_SUCCESS, LOGIN_ERROR,CLEAR
    }

    data class OperationResult(val status : OperationResultStatus, val any : Any? )

}
