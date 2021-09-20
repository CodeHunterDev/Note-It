package com.riyazuddin.noteit.data.remote.response

data class AuthResponse(
    val successful: Boolean,
    val message: String,
    val token: String? = null
)
