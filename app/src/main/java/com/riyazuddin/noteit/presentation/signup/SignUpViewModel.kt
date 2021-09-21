package com.riyazuddin.noteit.presentation.signup

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riyazuddin.noteit.common.Constants.AN_UNKNOWN_ERROR_OCCURRED
import com.riyazuddin.noteit.common.Resource
import com.riyazuddin.noteit.presentation.states.AuthState
import com.riyazuddin.noteit.domain.use_cases.signup.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {


    private val _email = mutableStateOf("")
    val email: State<String> = _email
    fun setEmail(email: String) {
        _email.value = email
    }

    private val _password = mutableStateOf("")
    val password: State<String> = _password
    fun setPassword(password: String) {
        _password.value = password
    }

    private val _repeatPassword = mutableStateOf("")
    val repeatPassword: State<String> = _repeatPassword
    fun setRepeatPassword(repeatPassword: String) {
        _repeatPassword.value = repeatPassword
    }

    private val _signUpState = mutableStateOf(AuthState())
    val authState: State<AuthState> = _signUpState
    fun signUp() {
        signUpUseCase(email.value, password.value, repeatPassword.value).onEach {
            when (it) {
                is Resource.Loading -> {
                    _signUpState.value = AuthState(true)
                }
                is Resource.Success -> {
                    it.data?.let { authResponse ->
                        _signUpState.value = AuthState(
                            authSuccess = authResponse.successful,
                            authSuccessMessage = authResponse.message
                        )
                    } ?: run {
                        _signUpState.value =
                            AuthState(error = it.message ?: AN_UNKNOWN_ERROR_OCCURRED)
                    }

                }
                is Resource.Error -> {
                    _signUpState.value =
                        AuthState(error = it.message ?: AN_UNKNOWN_ERROR_OCCURRED)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun reAssignSignUpState(){
        _signUpState.value = AuthState()
    }
}