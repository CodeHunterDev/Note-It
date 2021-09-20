package com.riyazuddin.noteit.common

sealed class Screen (val route: String){
    object LoginScreen: Screen("login_screen")
    object SignUpScreen : Screen("sign_up_screen")
    object NotesScreen: Screen("notes_screen")
    object CreateNoteScreen: Screen("create_note_screen")
    object SettingsScreen: Screen("settings_screen")
}