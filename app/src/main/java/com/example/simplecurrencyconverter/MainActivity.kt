package com.example.simplecurrencyconverter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.core.common.navigation.NavigationContract
import com.example.core.storage.PreferenceManager
import com.facebook.stetho.Stetho

class MainActivity : AppCompatActivity(), NavigationContract {

    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Stetho.initializeWithDefaults(this)

        navController = findNavController(R.id.nav_host_fragment)

        if(PreferenceManager(this).checkIfUserAlreadyLoggedIn()){
            navigateTo(1,Bundle())
        }else{
            //go to login
        }
    }

    override fun navigateTo(destination: Int, data: Bundle) {
        when(destination){
            1-> navController.navigate(R.id.action_login_fragment_to_conversor_fragment,data)
            2-> navController.navigate(R.id.action_conversor_fragment_to_result_operation_fragment,data)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if(PreferenceManager(this).checkIfUserAlreadyLoggedIn())
            finish()
    }
}
