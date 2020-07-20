package com.example.converter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.converter.data.CurrencyHelper
import com.example.converter.data.RetrofitCurrencyBuilder
import com.example.converter.data.model.Cripto
import com.example.converter.data.model.ReportData
import com.example.core.data.Resource
import com.example.core.storage.PreferenceManager
import kotlinx.coroutines.Dispatchers
import java.net.UnknownHostException

class ConversorViewModel : ViewModel() {

    var currentCurrency = Currency.USD
    var currentFloatVal = 0f
    var uiStateManager = MutableLiveData<UIState>()
    val operationResponse = MutableLiveData<Result>()
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
            if(exception is UnknownHostException){
                emit(Resource.error(data = null, message = "There is no internet connection available, please try again later." ?: "Error Occurred!"))
            }else
                emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun successRetrievedCurrencies(data: List<Cripto>?) {

        /**
         * First we're checking how many usd dollars we need to get one of the other currencies.
         * for instance, we need 9500 usd to get 1 BTC.
         */
        btc = data?.find { it.symbol == Currency.BTC.name }?.quote?.currency?.priceAgainstUsd ?: 9500f
        eth = data?.find { it.symbol == Currency.ETH.name }?.quote?.currency?.priceAgainstUsd ?: 234.8856f
        ptr = 60f
        eur = 1.141f
        bs = 0.00001f
        usd = 1f

        // Now according what the user set on the screen, we calculate how much is that in USD
        val currentFloatValToReport = convertInputToUsd()

        // After that, we do the conversion from what we know that the user set on the screen
        //(in usd) to the other currencies.
        val valInBtc = currentFloatValToReport / btc
        val valInEth= currentFloatValToReport / eth
        val valInPtr = currentFloatValToReport / ptr
        val valInEur = currentFloatValToReport / eur
        val valInBs = currentFloatValToReport / bs
        val valInUsd = currentFloatValToReport / usd

        /**
         * now I convert the exchange rate that I received from the api to the expected
         * exchange rate according to the currency set by the user before on the screen.
         */
        when(currentCurrency){
            Currency.USD->{

            }
            Currency.EUR ->{

                btc /= eur
                eth /= eur
                ptr /= eur
                bs /= eur
                usd /= eur
                eur = 1f

            }
            Currency.BS -> {
                btc /= bs
                eth /= bs
                ptr /= bs
                eur /= bs
                usd /= bs
                bs /= bs

            }
            Currency.BTC ->{
                eth /= btc
                ptr /= btc
                eur /= btc
                usd /= btc
                bs /= btc
                btc /= btc
            }
            Currency.ETH -> {
                ptr /= eth
                eur /= eth
                usd /= eth
                bs /= eth
                btc /= eth
                eth /= eth
            }
            Currency.PTR -> {
                eur /= ptr
                usd /= ptr
                bs /= ptr
                btc /= ptr
                eth /= ptr
                ptr /= ptr
            }
        }


        val dataToReport = ReportData(
            initialValue = currentFloatVal.toString(),
            initialCurrency = currentCurrency.name,
            valueInUsd = valInUsd.toString(),
            exchangeRateInUsd =  usd.toString(),
            valueInEur = valInEur.toString(),
            exchangeRateInEur = eur.toString(),
            valueInBs =  valInBs.toString(),
            exchangeRateInBs = bs.toString(),
            valueInBtc = valInBtc.toString(),
            exchangeRateInBtc = btc.toString(),
            valueInEth = valInEth.toString(),
            exchangeRateInEth = eth.toString(),
            valueInPtr = valInPtr.toString(),
            exchangeRateInPtr = ptr.toString()
            )

        operationResponse.postValue(Result(false,dataToReport))
    }

    private fun convertInputToUsd(): Float {



        val currentFloatVal = when(currentCurrency){
            Currency.USD-> return currentFloatVal
            Currency.EUR -> { currentFloatVal * eur }
            Currency.BS -> { currentFloatVal * bs }
            Currency.BTC -> btc*currentFloatVal
            Currency.ETH -> eth*currentFloatVal
            Currency.PTR -> ptr*currentFloatVal
        }

        return currentFloatVal
    }

    fun clearData(preferenceManager: PreferenceManager) {
        preferenceManager.removeUserToken()
    }

    data class UIState (val uiState : UIStateOption)
    data class Result (val cache : Boolean , val data : ReportData?)

    enum class UIStateOption {
        EMPTY, WRONG_VALUE, CORRECT_VALUE
    }

    enum class Currency {
        USD,EUR,BS,BTC,ETH,PTR
    }
}
