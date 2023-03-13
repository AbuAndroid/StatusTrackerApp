package com.example.statustracker.ui.loginScreen

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import com.example.statustracker.R
import com.example.statustracker.model.User

class LoginViewModel : ViewModel(){

    var appJustLaunched by mutableStateOf(true)
    var userAuthenticated by mutableStateOf(false)
    private var user by mutableStateOf(User())

    private lateinit var account : Auth0
    @SuppressLint("StaticFieldLeak")
    private lateinit var context : Context


    fun setContext(activityContext : Context){
        context = activityContext
        account = Auth0(
            context.getString(R.string.com_auth_client_id),
            context.getString(R.string.com_auth_domain)
        )
    }

    fun login(){
        WebAuthProvider
            .login(account)
            .withScheme(context.getString(R.string.com_auth_scheme))
            .withScope("openid profile email")
            .start(context,object : Callback<Credentials, AuthenticationException> {
                override fun onFailure(error: AuthenticationException) {
                    Log.e("main","Error Occured Login():$error")
                }

                override fun onSuccess(result: Credentials) {
                    val idToken = result.idToken
                    Log.e("cl",result.accessToken)
                    user = User(idToken)
                    userAuthenticated = true
                    appJustLaunched = false

                    updateUi()
                }
            })
    }

    fun logout(){
        WebAuthProvider
            .logout(account)
            .withScheme(context.getString(R.string.com_auth_scheme))
            .start(context,object : Callback<Void?, AuthenticationException> {
                override fun onFailure(error: AuthenticationException) {
                    Log.e("main", "Error occurred in logout(): ${error.toString()} ")
                }

                override fun onSuccess(result: Void?) {
                    userAuthenticated = false
                    updateUi()
                }
            })

    }

    private fun updateUi() {
        if(userAuthenticated){

        }
    }

}