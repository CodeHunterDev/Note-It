package com.riyazuddin.noteit.presentation.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riyazuddin.noteit.common.TextFieldState
import com.riyazuddin.noteit.common.UIEvent
import com.riyazuddin.noteit.domain.use_cases.login.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _email = mutableStateOf(TextFieldState())
    val email: State<TextFieldState> = _email

    private val _password = mutableStateOf(TextFieldState())
    val password: State<TextFieldState> = _password

    private val _passwordToggleState = mutableStateOf(true)
    val passwordToggleState: State<Boolean> = _passwordToggleState

    private val _loginInProgress = mutableStateOf(false)
    val loginInProgress: State<Boolean> = _loginInProgress
    private fun setLoginInProgress(isInProgress: Boolean) {
        _loginInProgress.value = isInProgress
    }

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow  = _eventFlow.asSharedFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EnteredEmail -> {
                _email.value = email.value.copy(
                    text = event.email,
                    error = ""
                )
            }
            is LoginEvent.EnteredPassword -> {
                _password.value = password.value.copy(
                    text = event.password,
                    error = ""
                )
            }
            is LoginEvent.PasswordToggle -> {
                _passwordToggleState.value = event.toggle
            }
            LoginEvent.Login -> {
                setLoginInProgress(true)
                viewModelScope.launch {
                    loginUseCase(email.value.text, password.value.text).collect { loginStateResult ->
                        when (loginStateResult) {
                            is LoginState.EmailError -> {
                                _email.value = email.value.copy(
                                    error = loginStateResult.error
                                )
                                setLoginInProgress(false)
                            }
                            is LoginState.PasswordError -> {
                                _password.value = password.value.copy(
                                    error = loginStateResult.error
                                )
                                setLoginInProgress(false)
                            }
                            is LoginState.ProcessingLoginEvent -> {
                                setLoginInProgress(true)
                            }
                            is LoginState.ErrorResponse -> {
                                setLoginInProgress(false)
                                _eventFlow.emit(UIEvent.ShowSnackbar(loginStateResult.error))
                            }
                            is LoginState.SuccessResponse -> {
                                setLoginInProgress(false)
                                _eventFlow.emit(UIEvent.ShowSnackbar(loginStateResult.successMessage))
                                _eventFlow.emit(UIEvent.OnSuccessEvent)
                            }
                        }
                    }
                }
            }
        }
    }

}