package com.example.converter

import androidx.lifecycle.ViewModel

class ConversorViewModel : ViewModel() {


    fun manageSelectedCurrency(position: Int) {

        println("")
    }

    private fun getCurrencyByPosition(position : Int){

        when (position){
            0 -> Currency.USD
            1 -> Currency.EUR
            2 -> Currency.BS
            3 -> Currency.BTC
            4 -> Currency.ETH
            5 -> Currency.PTR
        }
    }
    enum class Currency {
        USD,EUR,BS,BTC,ETH,PTR
    }
}
