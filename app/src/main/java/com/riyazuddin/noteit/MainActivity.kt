package com.riyazuddin.noteit

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.riyazuddin.noteit.common.Constants.NO_TOKEN
import com.riyazuddin.noteit.common.Constants.TOKEN_KEY
import com.riyazuddin.noteit.common.Navigation
import com.riyazuddin.noteit.common.Screen
import com.riyazuddin.noteit.presentation.ui.theme.NoteItTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var application: NoteItApplication

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    @DelicateCoroutinesApi
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoteItTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val t = sharedPreferences.getString(TOKEN_KEY, NO_TOKEN) ?: NO_TOKEN
                    if (t != NO_TOKEN)
                        Navigation(startDestination = Screen.NotesScreen.route)
                    else
                        Navigation(startDestination = Screen.LoginScreen.route)
                }
            }
        }
    }
}