package com.riyazuddin.noteit.presentation.states

sealed class SignUpValidateState(val error: String = "") {
    class EmailError(error: String) : SignUpValidateState(error)
    class PasswordError(error: String): SignUpValidateState(error)
    class RepeatPasswordError(error: String): SignUpValidateState(error)
    object Valid: SignUpValidateState()
}