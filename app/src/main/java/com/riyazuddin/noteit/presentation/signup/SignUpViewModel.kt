package com.riyazuddin.noteit.presentation.signup

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riyazuddin.noteit.domain.use_cases.signup.SignUpUseCase
import com.riyazuddin.noteit.presentation.states.SignUpState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    private val _email = mutableStateOf(SignUpTextFieldState(text = "riyazuddin515@gmail.com"))
    val email: State<SignUpTextFieldState> = _email

    private val _password = mutableStateOf(SignUpTextFieldState("Qa123456"))
    val password: State<SignUpTextFieldState> = _password

    private val _repeatPassword = mutableStateOf(SignUpTextFieldState("Qa123456"))
    val repeatPassword: State<SignUpTextFieldState> = _repeatPassword

    private val _eventFlow = MutableSharedFlow<UIEventSignUp>()
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
                                _eventFlow.emit(UIEventSignUp.ShowSnackbar(result.error))
                                setSignUpInProgress(false)
                            }
                            is SignUpState.SuccessResponse -> {
                                _eventFlow.emit(UIEventSignUp.ShowSnackbar(result.successMessage))
                                _eventFlow.emit(UIEventSignUp.SignUp)
                                setSignUpInProgress(false)
                            }
                            else -> {}
                        }
                    }
                }
            }
        }
    }

    sealed class UIEventSignUp {
        data class ShowSnackbar(val message: String) : UIEventSignUp()
        object SignUp : UIEventSignUp()
    }
}