package com.riyazuddin.noteit.presentation.register

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(): ViewModel(){

    private val _email = mutableStateOf("")
    val email: State<String> = _email
    fun setEmail(email: String){
        _email.value = email
    }

    private val _password = mutableStateOf("")
    val password: State<String> = _password
    fun setPassword(password: String){
        _password.value = password
    }

    private val _repeatPassword = mutableStateOf("")
    val repeatPassword: State<String> = _repeatPassword
    fun setRepeatPassword(repeatPassword: String){
        _repeatPassword.value = repeatPassword
    }
}