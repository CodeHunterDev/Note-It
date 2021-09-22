package com.riyazuddin.noteit.common

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import com.riyazuddin.noteit.data.model.Note
import com.riyazuddin.noteit.presentation.login.LoginScreen
import com.riyazuddin.noteit.presentation.note.CreateNoteScreen
import com.riyazuddin.noteit.presentation.notes.NotesScreen
import com.riyazuddin.noteit.presentation.settings.SettingsScreen
import com.riyazuddin.noteit.presentation.signup.SignUpScreen

@ExperimentalAnimationApi
@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.NotesScreen.route
    ) {
        composable(route = Screen.LoginScreen.route) {
            LoginScreen(navController = navController)
        }
        composable(route = Screen.SignUpScreen.route) {
            SignUpScreen(navController = navController)
        }
        composable(route = Screen.NotesScreen.route) {
            NotesScreen(navController = navController)
        }
        composable(
            route = "${Screen.CreateNoteScreen.route}/{note}",
            arguments = listOf(navArgument("note") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("note")?.let {
                val user = Gson().fromJson(it, Note::class.java)
                CreateNoteScreen(navController = navController, user)
            }
        }
        composable(route = Screen.SettingsScreen.route) {
            SettingsScreen(navController = navController)
        }
    }
}