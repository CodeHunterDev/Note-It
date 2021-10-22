package com.riyazuddin.noteit.data.repository

import com.riyazuddin.noteit.common.Constants.AN_UNKNOWN_ERROR_OCCURRED
import com.riyazuddin.noteit.common.Resource
import com.riyazuddin.noteit.data.remote.NoteItApi
import com.riyazuddin.noteit.data.remote.request.AccountRequest
import com.riyazuddin.noteit.data.remote.response.AuthResponse
import com.riyazuddin.noteit.domain.repository.IAuthRepository
import javax.inject.Inject

class AuthRepositoryImp @Inject constructor(
    private val noteItApi: NoteItApi
) : IAuthRepository {

    override suspend fun signUp(email: String, password: String): AuthResponse {
        return try {
            val response = noteItApi.signUp(AccountRequest(email, password))
            if (response.isSuccessful)
                response.body() ?: return AuthResponse()
            else
                return AuthResponse()
        } catch (e: Exception) {
            AuthResponse(message = e.message.toString())
        }
    }

    override suspend fun login(email: String, password: String): AuthResponse {
        return try {
            val response = noteItApi.login(AccountRequest(email, password))
            if (response.isSuccessful)
                response.body() ?: return AuthResponse()
            else
                return AuthResponse()
        } catch (e: Exception) {
            return AuthResponse(message = e.message.toString())
        }
    }
}