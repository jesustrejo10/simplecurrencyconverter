package com.example.login

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

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
            operationResultManager.postValue(OperationResult.LOGIN_REQUESTED)
        }

    }

    enum class UIValidator {
        INVALID_EMAIL, INVALID_PASSWORD,INVALID_EMAIL_LENGHT
    }

    enum class OperationResult {
        LOGIN_REQUESTED
    }
}
