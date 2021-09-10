package com.riyazuddin.noteit.presentation.utill

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.riyazuddin.noteit.presentation.login.LoginScreen
import com.riyazuddin.noteit.presentation.register.SignUpScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.LoginScreen.route
    ) {
        composable(route = Screen.LoginScreen.route) {
            LoginScreen(navController = navController)
        }
        composable(route = Screen.SignUpScreen.route) {
            SignUpScreen(navController = navController)
        }
    }
}