package com.riyazuddin.noteit.domain.repository

import com.riyazuddin.noteit.data.remote.response.AuthResponse

interface IAuthRepository {

    suspend fun signUp(email: String, password: String): AuthResponse

    suspend fun login(email: String, password: String): AuthResponse

}