package com.riyazuddin.noteit.domain.use_cases.signup

import com.riyazuddin.noteit.common.Resource
import com.riyazuddin.noteit.data.remote.request.AccountRequest
import com.riyazuddin.noteit.data.remote.response.AuthResponse
import com.riyazuddin.noteit.data.remote.response.SimpleResponse
import com.riyazuddin.noteit.domain.repository.IAuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val repository: IAuthRepository
){
    operator fun invoke(
        email: String,
        password: String
    ): Flow<Resource<AuthResponse>> = flow {
        emit(Resource.Loading())
        val response = repository.signUp(
            AccountRequest(email, password)
        )
        emit(response)
    }
}