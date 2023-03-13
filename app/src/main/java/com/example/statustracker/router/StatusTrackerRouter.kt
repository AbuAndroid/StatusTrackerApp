package com.example.statustracker.router

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.statustracker.model.WorkStatusItemModel
import com.example.statustracker.ui.homescreen.HomeScreenViewModel
import com.example.statustracker.ui.homescreen.WorkStatusScreen
import com.example.statustracker.ui.loginScreen.LoginScreen
import com.example.statustracker.ui.loginScreen.LoginViewModel
import com.example.statustracker.ui.splashscreen.SplashScreen

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun StatusTrackerRouter(
    loginViewModel: LoginViewModel,
    homeScreenViewModel: HomeScreenViewModel,
) {
    val uiState by homeScreenViewModel.uiState.collectAsState()

    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ){
        composable(route = Screen.Splash.route){
           SplashScreen(
               navController
           )
        }

        composable(route = Screen.Home.route){
            WorkStatusScreen(
                statusListItem = WorkStatusItemModel(
                    palaniSlackId = uiState.slackId,
                    balajiSlackId = uiState.slackId,
                    saranSlackId = uiState.slackId,
                    fazilSlackId = uiState.slackId,
                    maruthuSlackId = uiState.slackId,
                    abdullahSlackId = uiState.slackId,
                    date = uiState.date,
                    palani = uiState.palani,
                    balaji = uiState.balaji,
                    saran = uiState.saran,
                    fazil = uiState.fazil,
                    maruthu = uiState.maruthu,
                    abdullah = uiState.abdullah,
                    isLoading = uiState.isLoading,
                    lastUpdate = System.currentTimeMillis()
                ),
                alertOptions = homeScreenViewModel.alertOptions,
                onOptionSelected = {
                    homeScreenViewModel.onAlertOptionSelected()
                },
                onSubmitTask = {
                    homeScreenViewModel.onSubmitTask()
                },
                sendAlertToSlack = {
                    homeScreenViewModel.sendAlertToSlack()
                },
                isRefreshing = homeScreenViewModel.isRefreshing.value,
                onRefresh = {
                    homeScreenViewModel.refresh()
                },
                isVerified = uiState.isVerified
            )
        }

        composable(route = Screen.Login.route){
            val context = LocalContext.current
            LoginScreen(
                navController =  navController,
                isAuthenticated = loginViewModel.userAuthenticated,
                rquestLogin = {
                    loginViewModel.setContext(context)
                    loginViewModel.login()
                },
                requestLogout = {
                    loginViewModel.setContext(context)
                    loginViewModel.logout()
                }
            )
        }

    }

}