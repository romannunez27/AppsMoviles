package com.example.pasteleriamilsaboresapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pasteleriamilsaboresapp.ui.login.LoginScreen

@Composable

fun AppNav(){
    //Gestionar navegación
    val navController = rememberNavController()

    NavHost(navController=navController, startDestination = "login"){

        composable("login"){
            LoginScreen(navController = navController)
        }
    }
}