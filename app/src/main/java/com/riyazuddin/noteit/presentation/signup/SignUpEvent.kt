package com.riyazuddin.noteit.presentation.signup

sealed class SignUpEvent{
    data class EnteredEmail(val email: String): SignUpEvent()
    data class EnteredPassword(val password: String): SignUpEvent()
    data class EnteredRepeatedPassword(val repeatedPassword: String): SignUpEvent()
    object SignUp: SignUpEvent()
}
