package com.riyazuddin.noteit.presentation.utill

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.riyazuddin.noteit.presentation.note.CreateNoteScreen
import com.riyazuddin.noteit.presentation.notes.NotesScreen
import com.riyazuddin.noteit.presentation.login.LoginScreen
import com.riyazuddin.noteit.presentation.register.SignUpScreen
import com.riyazuddin.noteit.presentation.settings.SettingsScreen

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
        composable(route = Screen.CreateNoteScreen.route) {
            CreateNoteScreen(navController = navController)
        }
        composable(route = Screen.SettingsScreen.route) {
            SettingsScreen(navController = navController)
        }
    }
}