package com.example.statustracker.router

sealed class Screen(val route:String){
    object Splash : Screen("splash_screen")
    object Login : Screen("Login_screen")
    object Home : Screen("home_screen")
}
