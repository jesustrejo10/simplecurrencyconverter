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
import androidx.lifecycle.ViewModelProviders
import com.example.core.common.LoadingDialogFragment
import com.example.core.common.navigation.NavigationContract
import com.example.core.data.Resource
import com.example.core.data.Status
import com.example.core.storage.PreferenceManager
import com.example.login.LoginViewModel.UIValidator.*
import com.example.login.data.ServerLoginResponseModel
import com.example.login.data.UserServerModel
import kotlinx.android.synthetic.main.login_fragment.*
import kotlinx.coroutines.*


class LoginFragment : Fragment(), View.OnClickListener {

    private var viewModel: LoginViewModel? = null
    val dialog: DialogFragment = LoadingDialogFragment()
    private var myJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = null
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        viewModel?.preferenceManager =PreferenceManager(requireContext())
        login_action.setOnClickListener(this)
        viewModel?.operationResultManager?.observe(viewLifecycleOwner, Observer {
            manageOperationResult(it)
        })

        viewModel?.uiStatusManager?.observe(viewLifecycleOwner, Observer {
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
                viewModel?.verifyScreen(email.text.toString(),password.text.toString())
            }
        }
    }

    private fun manageOperationResult(operationResult: LoginViewModel.OperationResult?) {
        operationResult?:return
        when(operationResult.status){
            LoginViewModel.OperationResultStatus.LOGIN_REQUESTED ->{
                dialog.show(parentFragmentManager, "loading")
            }
            LoginViewModel.OperationResultStatus.LOGIN_SUCCESS ->{
                if (dialog.isVisible)
                    dialog.dismiss()
                Toast.makeText(context,getString(R.string.success_login),Toast.LENGTH_LONG).show()
                (activity as? NavigationContract)?.navigateTo(3,Bundle())
                viewModel?.operationResultManager?.postValue(
                    LoginViewModel.OperationResult(
                        LoginViewModel.OperationResultStatus.CLEAR,
                        null
                    )
                )

            }
            LoginViewModel.OperationResultStatus.LOGIN_ERROR-> {

                if (dialog.isVisible)
                    dialog.dismiss()
            }
            else -> {
                println("")
            }
        }
    }

    private fun displayGeneralError(message: String?) {

        Toast.makeText(activity,message,Toast.LENGTH_LONG).show()

    }
}
