package com.example.core.common

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.core.R
import kotlinx.android.synthetic.main.dialog_loading.*

class LoadingDialogFragment  : DialogFragment(), LoadingDialogControl {

    private val DIALOG_TAG = "loading_dialog"

    override fun showDialogFragment(fragmentManager : FragmentManager) {
        val fragment = fragmentManager.findFragmentByTag(DIALOG_TAG)
        if(fragment != null){
            if(!this.isAdded)
                this.show(fragmentManager,DIALOG_TAG)
        }else{
            if( !this.isAdded)
                this.show(fragmentManager,DIALOG_TAG)
        }
    }

    override fun dismissDialogFragment() {
        try{
            this.dismiss()
        }catch (e : Exception){
            println("Dialog already dimissed")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_loading, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        progressBarRounded.indeterminateDrawable.setColorFilter(Color.parseColor("#7cadf1"), android.graphics.PorterDuff.Mode.MULTIPLY)
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        isCancelable = false
    }


}