package com.example.converter.data.model.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider

import com.example.converter.R
import com.example.converter.data.model.ReportData
import kotlinx.android.synthetic.main.fragment_result_operation_fragment.*

class FragmentResultOperation : Fragment(), View.OnClickListener {

    companion object {
        fun newInstance() = FragmentResultOperation()
    }

    private lateinit var viewModel: FragmentResultOperationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_result_operation_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FragmentResultOperationViewModel::class.java)
        val data = viewModel.parseData(arguments?.getString("KEY_RESPONSE"))
        if(data == null){
            //todo display error and finish
        }else{
            manageData(data)
            restart.setOnClickListener(this)
            generate_qr.setOnClickListener(this)
        }
    }

    private fun manageData(data: ReportData) {

        originalValueUnit.text = data.initialCurrency
        originalValue.text = data.initialValue
        usdValue.text = data.valueInUsd
        usdExchangeRateValue.text = data.exchangeRateInUsd
        eurValue.text = data.valueInEur
        eurExchangeRateValue.text = data.exchangeRateInEur
        bsValue.text = data.valueInBs
        bsExchangeRateValue.text = data.exchangeRateInBs
        btcValue.text = data.valueInBtc
        btcExchangeRateValue.text = data.exchangeRateInBtc
        ethValue.text = data.valueInEth
        ethExchangeRateValue.text = data.exchangeRateInEth
        ptrValue.text = data.valueInPtr
        ptrExchangeRateValue.text = data.exchangeRateInPtr

    }

    override fun onClick(view: View?) {
        view?: return
        when(view.id){
            R.id.restart->{
                activity?.onBackPressed()
            }
            R.id.generate_qr->{
                viewModel.generateQR()
            }
        }

    }

}
