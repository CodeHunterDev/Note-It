package com.riyazuddin.noteit.domain.use_cases.login

import com.riyazuddin.noteit.common.Resource
import com.riyazuddin.noteit.data.remote.request.AccountRequest
import com.riyazuddin.noteit.data.remote.response.AuthResponse
import com.riyazuddin.noteit.domain.repository.IAuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: IAuthRepository
) {
    operator fun invoke(email: String, password: String): Flow<Resource<AuthResponse>> = flow {
        emit(Resource.Loading())
        emit(authRepository.login(AccountRequest(email, password)))
    }
}