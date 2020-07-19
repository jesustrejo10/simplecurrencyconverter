package com.example.converter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.converter.data.CurrencyHelper
import com.example.converter.data.RetrofitCurrencyBuilder
import com.example.converter.data.model.Cripto
import com.example.core.data.Resource
import kotlinx.coroutines.Dispatchers

class ConversorViewModel : ViewModel() {

    var currentCurrency = Currency.USD
    var currentFloatVal = 0f
    var uiStateManager = MutableLiveData<UIState>()

    var btc =  9500f
    var eth =  234.8856f
    var ptr = 60f
    var eur = 1.141f
    var bs = 0.00001f
    var usd = 1f

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


    fun requestCurrencies() = liveData(Dispatchers.IO) {

        emit(Resource.loading(data = null))
        try {
            val apiResponse = CurrencyHelper(RetrofitCurrencyBuilder.apiService).getCurrencies()
            emit(Resource.success(data =apiResponse.criptoList ))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun successRetrievedCurrencies(data: List<Cripto>?) {

        btc = data?.find { it.symbol == Currency.BTC.name }?.quote?.currency?.priceAgainstUsd ?: 9500f
        eth = data?.find { it.symbol == Currency.ETH.name }?.quote?.currency?.priceAgainstUsd ?: 234.8856f


        currentFloatVal = convertInputToUsd()

        val valInBtc = currentFloatVal / btc
        val valInEth= currentFloatVal / eth
        val valInPtr = currentFloatVal / ptr
        val valInEur = currentFloatVal / eur
        val valInBs = currentFloatVal / bs
        val valInUsd = currentFloatVal / usd

        println("")

    }

    private fun convertInputToUsd(): Float {

        currentFloatVal *= when(currentCurrency){
            Currency.USD-> return currentFloatVal
            Currency.EUR -> usd
            Currency.BS -> usd
            Currency.BTC -> btc
            Currency.ETH -> eth
            Currency.PTR -> ptr
        }

        return currentFloatVal
    }

    data class UIState (val uiState : UIStateOption)

    enum class UIStateOption {
        EMPTY, WRONG_VALUE, CORRECT_VALUE
    }

    enum class Currency {
        USD,EUR,BS,BTC,ETH,PTR
    }
}
