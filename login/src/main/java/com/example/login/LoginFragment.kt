package com.example.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.login.LoginViewModel.UIValidator.*
import kotlinx.android.synthetic.main.login_fragment.*


class LoginFragment : Fragment(), View.OnClickListener {

    private lateinit var viewModel: LoginViewModel

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


    }
}
