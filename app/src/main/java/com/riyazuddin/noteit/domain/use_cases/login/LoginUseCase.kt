package com.riyazuddin.noteit.domain.use_cases.login

import android.content.SharedPreferences
import com.riyazuddin.noteit.common.Constants
import com.riyazuddin.noteit.common.Constants.PASSWORD_REQUIRED
import com.riyazuddin.noteit.common.Validator
import com.riyazuddin.noteit.domain.repository.IAuthRepository
import com.riyazuddin.noteit.presentation.login.LoginState
import com.riyazuddin.noteit.presentation.signup.SignUpState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: IAuthRepository,
    private val sharedPreferences: SharedPreferences
) {
    operator fun invoke(email: String, password: String): Flow<LoginState> {
        return flow {
            val emailResult = Validator.validateEmail(email)
            if (emailResult != Constants.VALID)
                return@flow emit(LoginState.EmailError(emailResult))

            if (password.trim().isEmpty())
                return@flow emit(LoginState.PasswordError(PASSWORD_REQUIRED))

            emit(LoginState.ProcessingLoginEvent(true))

            val response = authRepository.login(email, password)
            if (response.successful) {
                val token = response.token ?: Constants.NO_TOKEN
                sharedPreferences.edit().putString(Constants.TOKEN_KEY, token).apply()
                Timber.i(response.toString())
                return@flow emit(LoginState.SuccessResponse(response.message))
            }else{
                return@flow emit(LoginState.ErrorResponse(response.message))
            }
        }
    }
}