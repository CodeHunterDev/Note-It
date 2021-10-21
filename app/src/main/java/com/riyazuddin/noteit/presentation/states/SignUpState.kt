package com.riyazuddin.noteit.presentation.states

sealed class SignUpState(val error: String = "", val successMessage: String = "") {
    class EmailError(error: String) : SignUpState(error)
    class PasswordError(error: String) : SignUpState(error)
    class RepeatPasswordError(error: String) : SignUpState(error)
    object ValidInput : SignUpState()

    data class ProcessingSignUpEvent(val inProgress: Boolean) : SignUpState()

    class ErrorResponse(error: String) : SignUpState(error)
    class SuccessResponse(successMessage: String): SignUpState(successMessage)
}