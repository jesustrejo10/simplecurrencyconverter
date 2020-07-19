package com.example.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.core.common.LoadingDialogFragment
import com.example.core.common.navigation.NavigationContract
import com.example.core.data.Resource
import com.example.core.data.Status
import com.example.core.storage.PreferenceManager
import com.example.login.LoginViewModel.UIValidator.*
import com.example.login.data.ServerLoginResponseModel
import com.example.login.data.UserServerModel
import kotlinx.android.synthetic.main.login_fragment.*


class LoginFragment : Fragment(), View.OnClickListener {

    private lateinit var viewModel: LoginViewModel
    val dialog: DialogFragment = LoadingDialogFragment()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        login_action.setOnClickListener(this)
        viewModel.operationResultManager.observe(viewLifecycleOwner, Observer {
            manageOperationResult(it)
        })

        viewModel.uiStatusManager.observe(viewLifecycleOwner, Observer {
            manageUIStatus(it)
        })


    }

    private fun manageUIStatus(uiStatus: LoginViewModel.UIValidator?) {
        uiStatus ?: return
        when(uiStatus){
            INVALID_EMAIL ->{
                email.requestFocus()
                email.error = getString(R.string.wrong_email_address)
            }
            INVALID_PASSWORD -> {
                password.requestFocus()
                password.error = getString(R.string.empty_password)
            }
            INVALID_EMAIL_LENGHT -> {
                password.requestFocus()
                password.error = getString(R.string.wrong_password)
            }
        }
    }

    override fun onClick(view: View?) {
        if(view != null){
            if(view.id == R.id.login_action){
                viewModel.verifyScreen(email.text.toString(),password.text.toString())
            }
        }
    }

    private fun manageOperationResult(operationResult: LoginViewModel.OperationResult?) {
        operationResult?:return
        when(operationResult.status){
            LoginViewModel.OperationResultStatus.LOGIN_REQUESTED ->{
                dialog.show(parentFragmentManager, "loading")

                val data = operationResult.any as? UserServerModel ?: run{
                    displayGeneralError(null)
                    return
                }
                viewModel.login(data).observe(viewLifecycleOwner, Observer {
                    manageAPIResponse(it)
                })

            }
            LoginViewModel.OperationResultStatus.LOGIN_SUCCESS ->{
                Toast.makeText(context,getString(R.string.success_login),Toast.LENGTH_LONG).show()
                (activity as? NavigationContract)?.navigateTo(1,Bundle())
            }
        }
    }

    private fun manageAPIResponse(it: Resource<ServerLoginResponseModel>) {
        when(it.status){
            Status.SUCCESS ->{
                context?.let {context ->
                    viewModel.successLogin(it.data, PreferenceManager(context))

                }
                if(dialog.isVisible)
                    dialog.dismiss()
            }
            Status.ERROR -> {
                if(dialog.isVisible)
                    dialog.dismiss()
                displayGeneralError(it.message)
            }

        }

    }

    private fun displayGeneralError(message: String?) {



    }
}
