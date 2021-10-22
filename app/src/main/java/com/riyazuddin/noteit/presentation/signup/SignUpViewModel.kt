package com.riyazuddin.noteit.presentation.signup

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riyazuddin.noteit.common.TextFieldState
import com.riyazuddin.noteit.common.UIEvent
import com.riyazuddin.noteit.domain.use_cases.signup.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    private val _email = mutableStateOf(TextFieldState())
    val email: State<TextFieldState> = _email

    private val _password = mutableStateOf(TextFieldState())
    val password: State<TextFieldState> = _password

    private val _repeatPassword = mutableStateOf(TextFieldState())
    val repeatPassword: State<TextFieldState> = _repeatPassword

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _signUpInProgress = mutableStateOf(false)
    val signUpInProgress: State<Boolean> = _signUpInProgress
    private fun setSignUpInProgress(isInProgress: Boolean) {
        _signUpInProgress.value = isInProgress
    }

    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.EnteredEmail -> {
                _email.value = email.value.copy(
                    text = event.email,
                    error = ""
                )
            }
            is SignUpEvent.EnteredPassword -> {
                _password.value = password.value.copy(
                    text = event.password,
                    error = ""
                )
            }
            is SignUpEvent.EnteredRepeatedPassword -> {
                _repeatPassword.value = repeatPassword.value.copy(
                    text = event.repeatedPassword,
                    error = ""
                )
            }
            SignUpEvent.SignUp -> {
                setSignUpInProgress(true)
                viewModelScope.launch {
                    signUpUseCase(
                        email.value.text,
                        password.value.text,
                        repeatPassword.value.text
                    ).collect { result ->
                        when (result) {
                            is SignUpState.EmailError -> {
                                _email.value = _email.value.copy(error = result.error)
                                setSignUpInProgress(false)
                            }
                            is SignUpState.PasswordError -> {
                                _password.value = _password.value.copy(error = result.error)
                                setSignUpInProgress(false)
                            }
                            is SignUpState.RepeatPasswordError -> {
                                _repeatPassword.value = _repeatPassword.value.copy(error = result.error)
                                setSignUpInProgress(false)
                            }
                            is SignUpState.ProcessingSignUpEvent -> {
                                _signUpInProgress.value = true
                            }
                            is SignUpState.ErrorResponse -> {
                                _eventFlow.emit(UIEvent.ShowSnackbar(result.error))
                                setSignUpInProgress(false)
                            }
                            is SignUpState.SuccessResponse -> {
                                _eventFlow.emit(UIEvent.ShowSnackbar(result.successMessage))
                                _eventFlow.emit(UIEvent.OnSuccessEvent)
                                setSignUpInProgress(false)
                            }
                        }
                    }
                }
            }
        }
    }
}