package com.riyazuddin.noteit.common

import com.riyazuddin.noteit.common.Constants.EMAIL_REQUIRED
import com.riyazuddin.noteit.common.Constants.INVALID_EMAIL
import com.riyazuddin.noteit.common.Constants.PASSWORD_DOES_NOT_MATCH
import com.riyazuddin.noteit.common.Constants.PASSWORD_REQUIRED
import com.riyazuddin.noteit.common.Constants.PASSWORD_REQUIREMENT
import com.riyazuddin.noteit.common.Constants.VALID
import com.riyazuddin.noteit.presentation.signup.SignUpState
import java.util.regex.Pattern

object Validator {

    fun validateEmail(email: String): String {
        val trimmedEmail = email.trim()
        if (trimmedEmail.isEmpty())
            return EMAIL_REQUIRED
        val regex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$"
        val patter = Pattern.compile(regex)
        if (!patter.matcher(trimmedEmail).matches())
            return INVALID_EMAIL
        return VALID
    }

    fun validatePassword(password: String): String {
        val trimmedPassword = password.trim()
        if (trimmedPassword.isEmpty())
            return PASSWORD_REQUIRED
        val containCap = password.any { it.isUpperCase() }
        val containDigit = password.any { it.isDigit() }
        val containSmall = password.any { it.isLowerCase() }
        if (!containCap || !containDigit || !containSmall || trimmedPassword.length < 8)
            return PASSWORD_REQUIREMENT

        return VALID
    }

    fun validateRepeatPassword(password: String, repeatPassword: String): String {
        if (password.trim() != repeatPassword.trim())
            return PASSWORD_DOES_NOT_MATCH
        return VALID
    }
}