package com.example.statustracker.ui.loginScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

//@Preview(showBackground = true)
//@Composable
//fun LoginScreenPreview(){
//    LoginScreen(navController)
//}

@Composable
fun LoginScreen(
    navController: NavHostController,
    rquestLogin: () -> Unit,
    requestLogout:()->Unit,
    isAuthenticated: Boolean
) {

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement =Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if(isAuthenticated){
            Text(text = "User authenicated suuccess fully ")
        }else{
            Text(text = "user authenticated Failure")
        }

        Button(
            onClick = {
                rquestLogin()
        }) {
           Text(text = "Login")
        }

        Button(
            onClick = {
                requestLogout()
            }
        ){
            Text(text = "Log out")
        }
    }
}