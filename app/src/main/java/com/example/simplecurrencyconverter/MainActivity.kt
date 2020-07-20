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
            navigateTo(0,Bundle())
        }
    }

    override fun navigateTo(destination: Int, data: Bundle) {
        when(destination){
            0-> {
                currentState = "login"
                navController.navigate(R.id.action_blank_fragment_to_login_fragment, data)
            }
            1-> {
                currentState ="converter"
                navController.navigate(R.id.action_blank_fragment_to_conversor_fragment, data)
            }
            2-> {
                currentState ="result"
                navController.navigate(R.id.action_conversor_fragment_to_result_operation_fragment,data)
            }
            3->{
                currentState ="converter"
                navController.navigate(R.id.action_login_fragment_to_conversor_fragment,data)
            }
        }
    }

    override fun updateScreen(screen: String) {
        currentState  = screen
    }

    companion object{
        var currentState = ""
    }

    override fun onBackPressed() {
        when(currentState){

            "login" -> finish()
            "converter" ->finish()
            else->{
                super.onBackPressed()
            }
        }
    }
}
