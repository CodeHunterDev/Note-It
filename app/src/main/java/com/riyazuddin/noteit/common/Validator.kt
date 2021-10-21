package com.riyazuddin.noteit.common

import com.riyazuddin.noteit.presentation.states.SignUpState
import java.util.regex.Pattern

object Validator {

    fun validateEmail(email: String): SignUpState {
        val trimmedEmail = email.trim()
        if (trimmedEmail.isEmpty())
            return SignUpState.EmailError("E-mail required")
        val regex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$"
        val patter = Pattern.compile(regex)
        if (!patter.matcher(trimmedEmail).matches())
            return SignUpState.EmailError("Invalid E-mail")
        return SignUpState.ValidInput
    }

    fun validatePassword(password: String): SignUpState {
        val trimmedPassword = password.trim()
        if (trimmedPassword.isEmpty())
            return SignUpState.PasswordError("Password required")
        val containCap = password.any { it.isUpperCase() }
        val containDigit = password.any { it.isDigit() }
        val containSmall = password.any { it.isLowerCase() }
        if (!containCap || !containDigit || !containSmall)
            return SignUpState.PasswordError("Password should contain Lower case, upper case and digit")
        if (trimmedPassword.length < 8)
            return SignUpState.PasswordError("Password Length should be 8")
        return SignUpState.ValidInput
    }

    fun validateRepeatPassword(password: String, repeatPassword: String): SignUpState {
        if (password.trim() != repeatPassword.trim())
            return SignUpState.RepeatPasswordError("Doesn't match")
        return SignUpState.ValidInput
    }
}