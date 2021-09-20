package com.riyazuddin.noteit.presentation.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riyazuddin.noteit.common.Constants.AN_UNKNOWN_ERROR_OCCURRED
import com.riyazuddin.noteit.common.Resource
import com.riyazuddin.noteit.domain.use_cases.login.LoginUseCase
import com.riyazuddin.noteit.presentation.states.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
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

    private val _passwordToggleState = mutableStateOf(true)
    val passwordToggleState: State<Boolean> = _passwordToggleState
    fun setPasswordToggleState(passwordToggleState: Boolean) {
        _passwordToggleState.value = passwordToggleState
    }

    private val _loginState = mutableStateOf(AuthState())
    val loginState: State<AuthState> = _loginState
    fun login() {
        loginUseCase(email.value, password.value).onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    _loginState.value = AuthState(isLoading = true)
                }
                is Resource.Success -> {
                    resource.data.let { authResponse ->
                        authResponse?.let {
                            _loginState.value = AuthState(
                                authSuccess = it.successful,
                                authSuccessMessage = it.message
                            )
                            it.token?.let { token ->
                                Timber.i("Token : $token" )
                            }
                        } ?: kotlin.run {
                            _loginState.value = AuthState(authSuccess = false)
                        }
                    }
                }
                is Resource.Error -> {
                    _loginState.value =
                        AuthState(error = resource.message ?: AN_UNKNOWN_ERROR_OCCURRED)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun reAssignLoginState() {
        _loginState.value = AuthState()
    }

}