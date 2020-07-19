package com.example.converter.data.model.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.converter.R

class FragmentResultOperation : Fragment() {

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
        viewModel = ViewModelProviders.of(this).get(FragmentResultOperationViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
