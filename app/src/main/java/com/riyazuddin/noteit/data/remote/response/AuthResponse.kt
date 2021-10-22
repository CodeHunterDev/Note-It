package com.riyazuddin.noteit.data.remote.response

import com.riyazuddin.noteit.common.Constants.AN_UNKNOWN_ERROR_OCCURRED

data class AuthResponse(
    val successful: Boolean = false,
    val message: String = AN_UNKNOWN_ERROR_OCCURRED,
    val token: String? = null
)
