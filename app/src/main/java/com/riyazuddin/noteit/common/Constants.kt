package com.riyazuddin.noteit.common

object Constants {

    const val BASE_URL = "http://192.168.0.7:8080"

    val IGNORE_URLS = listOf("api/user/login", "api/user/signup")

    const val AN_UNKNOWN_ERROR_OCCURRED = "An Unknown error occurred."

    const val NOTES_DB_NAME = "notes_db"

    const val ENCRYPTED_SHARED_PREF_NAME = "enc_shar_pref"

    const val NO_TOKEN = "NO_TOKEN"
    const val TOKEN_KEY = NO_TOKEN

    const val EMAIL_REQUIRED = "E-mail required"
    const val INVALID_EMAIL = "Invalid E-mail"
    const val VALID = "Valid"

    const val PASSWORD_REQUIRED = "Password required"
    const val PASSWORD_REQUIREMENT = "Password should contain Lower case, upper case, digit and length 8"
    const val PASSWORD_DOES_NOT_MATCH = "Doesn't match"
}