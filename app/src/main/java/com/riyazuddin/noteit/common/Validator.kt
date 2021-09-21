package com.riyazuddin.noteit.common

import com.riyazuddin.noteit.presentation.states.SignUpValidateState
import com.riyazuddin.noteit.presentation.states.SignUpValidateState.*
import java.util.regex.Pattern

object Validator {

    fun signUpValidator(
        email: String,
        password: String,
        repeatPassword: String
    ): SignUpValidateState {
        val trimmedEmail = email.trim()
        if (trimmedEmail.isEmpty())
            return EmailError("E-mail required")
        val regex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$"
        val patter = Pattern.compile(regex)
        if (!patter.matcher(trimmedEmail).matches())
            return EmailError("Invalid E-mail")

        val trimmedPassword = password.trim()
        if (trimmedPassword.isEmpty())
            return PasswordError("Password required")
        if (trimmedPassword.length < 8)
            return PasswordError("Password is to short")
        val containCap = password.any { it.isUpperCase() }
        val containDigit = password.any { it.isDigit() }
        val containSmall = password.any { it.isLowerCase() }
        if (!containCap || !containDigit || !containSmall)
            return PasswordError("Password should contain Lower case, upper case and digit")

        if (password.trim() == repeatPassword.trim())
            return RepeatPasswordError("Doesn't match")
        return Valid
    }

    fun validateEmail(email: String): SignUpValidateState {
        val trimmedEmail = email.trim()
        if (trimmedEmail.isEmpty())
            return EmailError("E-mail required")
        val regex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$"
        val patter = Pattern.compile(regex)
        if (!patter.matcher(trimmedEmail).matches())
            return EmailError("Invalid E-mail")
        return Valid
    }

    fun validatePassword(password: String): SignUpValidateState {
        val trimmedPassword = password.trim()
        if (trimmedPassword.isEmpty())
            return PasswordError("Password required")
        if (trimmedPassword.length < 8)
            return PasswordError("Password is to short")
        val containCap = password.any { it.isUpperCase() }
        val containDigit = password.any { it.isDigit() }
        val containSmall = password.any { it.isLowerCase() }
        if (!containCap || !containDigit || !containSmall)
            return PasswordError("Password should contain Lower case, upper case and digit")
        return Valid
    }

    fun validateRepeatPassword(password: String, repeatPassword: String): SignUpValidateState {
        if (password.trim() == repeatPassword.trim())
            return RepeatPasswordError("Doesn't match")
        return Valid
    }
}