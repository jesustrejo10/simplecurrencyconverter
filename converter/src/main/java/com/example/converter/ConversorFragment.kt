package com.example.converter

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.converter.data.model.Cripto
import com.example.core.common.LoadingDialogFragment
import com.example.core.common.navigation.NavigationContract
import com.example.core.data.Resource
import com.example.core.data.Status
import com.example.core.storage.PreferenceManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.conversor_fragment.*


class ConversorFragment : Fragment(), AdapterView.OnItemClickListener,
    AdapterView.OnItemSelectedListener, View.OnClickListener {

    private lateinit var viewModel: ConversorViewModel
    val dialog: DialogFragment = LoadingDialogFragment()

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

        (activity as? NavigationContract)?.updateScreen("converter")
    }

    private fun manageViewComponents() {

        currencySpinner.onItemSelectedListener= this
        convert_action.setOnClickListener(this)
        logout.setOnClickListener(this)
        viewModel.uiStateManager.observe(viewLifecycleOwner, Observer {
            manageUIStatus(it)
        })
        viewModel.operationResponse.observe(viewLifecycleOwner, Observer {

            if(!cache)
            {
                val bundle = Bundle()
                val gson = Gson()
                bundle.putString("KEY_RESPONSE",gson.toJson(it.data))
                (activity as? NavigationContract)?.navigateTo(2,bundle)
                cache = true
            }

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
                dialog.show(parentFragmentManager, "loading")
                viewModel.requestCurrencies().observe(viewLifecycleOwner, Observer {
                    manageAPIResponse(it)
                })

            }
        }
    }

    override fun onResume() {
        super.onResume()
        if(dialog.isVisible)
            dialog.dismiss()
    }

    private fun manageAPIResponse(it: Resource<List<Cripto>>?) {
        when(it?.status){
            Status.SUCCESS ->{
                if(dialog.isVisible)
                    dialog.dismiss()
                context?.let {context ->
                    viewModel.successRetrievedCurrencies(it.data)
                }
            }
            Status.ERROR -> {
                dialog.dismiss()
                displayGeneralError(it.message)
            }

        }
    }

    private fun displayGeneralError(message: String?) {

        Toast.makeText(context,message,Toast.LENGTH_LONG).show()
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
            cache = false
            viewModel.verifyUi(currencyValue.text.toString())
        }
        if(v.id == R.id.logout){
            val dialogBuilder = AlertDialog.Builder(requireContext())

            // set message of alert dialog
            dialogBuilder.setMessage("Do you want to logout and close the application?")
                // if the dialog is cancelable
                .setCancelable(false)
                // positive button text and action
                .setPositiveButton("Proceed") { _, _ ->
                    run {
                        viewModel.clearData(PreferenceManager(requireContext()))
                        activity?.onBackPressed()
                        //(activity as? NavigationContract)?.updateScreen("login")
                    }
                }

                .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                        dialog, id -> dialog.cancel()
                })

            // create dialog box
            val alert = dialogBuilder.create()
            // set title for alert dialog box
            alert.setTitle("AlertDialogExample")
            // show alert dialog
            alert.show()
        }
    }
    companion object{
        var cache = false
    }

}
