package com.riyazuddin.noteit.domain.repository

import com.riyazuddin.noteit.common.Resource
import com.riyazuddin.noteit.data.model.Note
import com.riyazuddin.noteit.data.remote.request.AccountRequest
import com.riyazuddin.noteit.data.remote.response.AuthResponse
import com.riyazuddin.noteit.data.remote.response.SimpleResponse

interface IAuthRepository {

    suspend fun signUp(accountRequest: AccountRequest): Resource<AuthResponse>

    suspend fun login(accountRequest: AccountRequest): Resource<AuthResponse>

}