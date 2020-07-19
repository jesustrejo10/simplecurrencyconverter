package com.example.converter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.conversor_fragment.*


class ConversorFragment : Fragment(), AdapterView.OnItemClickListener,
    AdapterView.OnItemSelectedListener, View.OnClickListener {

    private lateinit var viewModel: ConversorViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.conversor_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ConversorViewModel::class.java)

        manageViewComponents()

    }

    private fun manageViewComponents() {

        currencySpinner.onItemSelectedListener= this
        convert_action.setOnClickListener(this)

        viewModel.uiStateManager.observe(viewLifecycleOwner, Observer {
            manageUIStatus(it)
        })
    }

    private fun manageUIStatus(uiState: ConversorViewModel.UIState?) {
        uiState?: return
        when(uiState.uiState){
            ConversorViewModel.UIStateOption.EMPTY -> {
                currencyValue.requestFocus()
                currencyValue.error = getString(R.string.empty_status)
            }
            ConversorViewModel.UIStateOption.WRONG_VALUE -> {

                currencyValue.requestFocus()
                currencyValue.error = getString(R.string.wrong_value)
            }
            ConversorViewModel.UIStateOption.CORRECT_VALUE ->{
                println("")
                //requestVal
            }
        }
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        viewModel.manageSelectedCurrency(position)
    }

    override fun onClick(v: View?) {
        v?: return
        if(v.id == R.id.convert_action){
            viewModel.verifyUi(currencyValue.text.toString())
        }
    }

}
