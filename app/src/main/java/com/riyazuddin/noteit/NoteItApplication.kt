package com.riyazuddin.noteit

import android.app.Application
import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class NoteItApplication : Application() {

    var isDarkMode = mutableStateOf(false)

    fun toggleTheme(){
        isDarkMode.value = !isDarkMode.value
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}