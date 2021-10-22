package com.riyazuddin.noteit.common

object Constants {

    const val BASE_URL = "http://192.168.0.7:8080"

    const val JWT_TOKEN = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJqd3QtYXVkaWVuY2UiLCJpc3MiOiJsb2NhbGhvc3Q6ODA4MCIsImV4cCI6MTY2MzE0MjMxOCwidXNlcklkIjoiNjEzYjcwNDVkZjQ3YjI3ZWZiMTg2MjZkIn0.aTobMxSX6lc0eJne6hIN43yyhMnBesGLTbPkZjTCPKI"

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