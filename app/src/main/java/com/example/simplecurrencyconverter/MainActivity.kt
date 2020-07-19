package com.example.simplecurrencyconverter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
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
            //go to dashboard
        }else{
            //go to login
        }
    }

    override fun navigateTo(destination: Int, data: Bundle) {
        when(destination){
            1->{
                //navController.navigate(1,data)

            }
        }
    }

}
