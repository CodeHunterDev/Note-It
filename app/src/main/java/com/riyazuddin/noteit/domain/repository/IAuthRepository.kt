package com.riyazuddin.noteit.domain.repository

import com.riyazuddin.noteit.common.Resource
import com.riyazuddin.noteit.data.model.Note
import com.riyazuddin.noteit.data.remote.request.AccountRequest
import com.riyazuddin.noteit.data.remote.response.AuthResponse
import com.riyazuddin.noteit.data.remote.response.SimpleResponse

interface IAuthRepository {

    suspend fun signUp(email: String, password: String): AuthResponse

    suspend fun login(email: String, password: String): Resource<AuthResponse>

}