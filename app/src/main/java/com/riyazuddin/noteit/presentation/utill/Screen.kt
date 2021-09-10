package com.riyazuddin.noteit.presentation.utill

sealed class Screen (val route: String){
    object LoginScreen: Screen("login_screen")
    object SignUpScreen : Screen("sign_up_screen")
}