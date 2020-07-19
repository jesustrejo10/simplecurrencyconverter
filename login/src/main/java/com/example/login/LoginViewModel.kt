package com.example.login

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.core.data.Resource
import com.example.core.storage.PreferenceManager
import com.example.login.data.LoginApiHelper
import com.example.login.data.RetrofitLoginBuilder
import com.example.login.data.ServerLoginResponseModel
import com.example.login.data.UserServerModel
import kotlinx.coroutines.Dispatchers

class LoginViewModel : ViewModel() {

    val uiStatusManager = MutableLiveData<UIValidator>()
    val operationResultManager = MutableLiveData<OperationResult>()

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
            operationResultManager.postValue(OperationResult(OperationResultStatus.LOGIN_REQUESTED,serverUserModel))
        }

    }




    fun login(serverUserModel: UserServerModel) = liveData(Dispatchers.IO) {

        emit(Resource.loading(data = null))
        try {
            val apiResponse = LoginApiHelper(RetrofitLoginBuilder.apiService).login(serverUserModel)
            emit(Resource.success(data =apiResponse ))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun successLogin(data: ServerLoginResponseModel?, preferenceManager: PreferenceManager) {
        if(data?.token.isNullOrEmpty())
            return

        preferenceManager.saveToken(data?.token!!)
        operationResultManager.postValue(OperationResult(OperationResultStatus.LOGIN_SUCCESS,data.token))
    }


    enum class UIValidator {
        INVALID_EMAIL, INVALID_PASSWORD,INVALID_EMAIL_LENGHT
    }

    enum class OperationResultStatus {
        LOGIN_REQUESTED,LOGIN_SUCCESS
    }

    data class OperationResult(val status : OperationResultStatus, val any : Any? )

}
