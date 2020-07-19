package com.example.converter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ConversorViewModel : ViewModel() {

    var currentCurrency = Currency.USD
    var currentFloatVal = 0f
    var uiStateManager = MutableLiveData<UIState>()

    fun manageSelectedCurrency(position: Int) {

        currentCurrency = getCurrencyByPosition(position)
    }

    private fun getCurrencyByPosition(position : Int): Currency {

        return when (position){
            0 -> Currency.USD
            1 -> Currency.EUR
            2 -> Currency.BS
            3 -> Currency.BTC
            4 -> Currency.ETH
            5 -> Currency.PTR
            else -> Currency.USD
        }
    }

    fun verifyUi(toString: String) {
        if(toString.isEmpty())
            uiStateManager.postValue(UIState(UIStateOption.EMPTY))
        else{
            val floatValue = toString.toFloatOrNull()
            if(floatValue == null)
                uiStateManager.postValue(UIState(UIStateOption.WRONG_VALUE))
            else{
                uiStateManager.postValue(UIState(UIStateOption.CORRECT_VALUE))
                currentFloatVal = floatValue
            }
        }
    }


    data class UIState (val uiState : UIStateOption)

    enum class UIStateOption {
        EMPTY, WRONG_VALUE, CORRECT_VALUE
    }

    enum class Currency {
        USD,EUR,BS,BTC,ETH,PTR
    }
}
