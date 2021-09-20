package com.riyazuddin.noteit.presentation.states

data class AuthState(
    val isLoading: Boolean = false,
    val authSuccess: Boolean = false,
    val authSuccessMessage: String = "",
    val error: String = ""
)
