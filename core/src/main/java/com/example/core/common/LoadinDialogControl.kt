package com.example.core.common

import androidx.fragment.app.FragmentManager

interface LoadingDialogControl {

    fun dismissDialogFragment()
    fun showDialogFragment(fragmentManager: FragmentManager)
}