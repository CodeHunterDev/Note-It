package com.riyazuddin.noteit.data.repository

import com.riyazuddin.noteit.common.Constants.AN_UNKNOWN_ERROR_OCCURRED
import com.riyazuddin.noteit.common.Resource
import com.riyazuddin.noteit.common.safeCall
import com.riyazuddin.noteit.data.remote.NoteItApi
import com.riyazuddin.noteit.data.remote.request.AccountRequest
import com.riyazuddin.noteit.data.remote.response.AuthResponse
import com.riyazuddin.noteit.domain.repository.IAuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepositoryImp @Inject constructor(
    private val noteItApi: NoteItApi
) : IAuthRepository {

    override suspend fun signUp(email: String, password: String): AuthResponse {
        return try {
            val response = noteItApi.signUp(AccountRequest(email, password))
            if (response.isSuccessful) {
                response.body() ?: return AuthResponse(false, AN_UNKNOWN_ERROR_OCCURRED)
            } else {
                return AuthResponse(false, AN_UNKNOWN_ERROR_OCCURRED)
            }
        }catch (e: Exception){
            AuthResponse(false, e.message.toString())
        }
    }

    override suspend fun login(email: String, password: String): Resource<AuthResponse> =
        withContext(Dispatchers.IO) {
            safeCall {
                val response = noteItApi.login(AccountRequest(email, password))
                if (response.isSuccessful) {
                    response.body()?.let {
                        if (it.successful)
                            return@safeCall Resource.Success(it)
                        else
                            return@safeCall Resource.Error(it.message)
                    } ?: return@safeCall Resource.Error(AN_UNKNOWN_ERROR_OCCURRED)
                } else
                    return@safeCall Resource.Error(response.message())
            }
        }
}