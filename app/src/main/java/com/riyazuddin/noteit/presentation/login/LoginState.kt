package com.riyazuddin.noteit.presentation.login

sealed class LoginState(val error: String = "", val successMessage: String = "") {
    class EmailError(error: String) : LoginState(error)
    class PasswordError(error: String) : LoginState(error)

    data class ProcessingLoginEvent(val inProgress: Boolean) : LoginState()

    class ErrorResponse(error: String) : LoginState(error)
    class SuccessResponse(successMessage: String): LoginState(successMessage)
}