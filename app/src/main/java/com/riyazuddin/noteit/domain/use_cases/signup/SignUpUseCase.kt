package com.riyazuddin.noteit.domain.use_cases.signup

import android.content.SharedPreferences
import com.riyazuddin.noteit.common.Constants
import com.riyazuddin.noteit.common.Constants.AN_UNKNOWN_ERROR_OCCURRED
import com.riyazuddin.noteit.common.Constants.NO_TOKEN
import com.riyazuddin.noteit.common.Validator
import com.riyazuddin.noteit.domain.repository.IAuthRepository
import com.riyazuddin.noteit.presentation.states.SignUpState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val repository: IAuthRepository,
    private val sharedPreferences: SharedPreferences
) {
    suspend operator fun invoke(
        email: String,
        password: String,
        repeatPassword: String
    ): Flow<SignUpState> {
        return flow {
            val emailResult = Validator.validateEmail(email)
            if (emailResult.error.isNotEmpty())
                emit(emailResult)

            val passwordResult = Validator.validatePassword(password)
            if (passwordResult.error.isNotEmpty())
                emit(passwordResult)

            val repeatedPasswordResult = Validator.validateRepeatPassword(password, repeatPassword)
            if (repeatedPasswordResult !is SignUpState.ValidInput)
                emit(repeatedPasswordResult)

            emit(SignUpState.ProcessingSignUpEvent(true))

            val response = repository.signUp(email, password)
            if (response.successful) {
                val token = response.token ?: NO_TOKEN
                sharedPreferences.edit().putString(Constants.TOKEN_KEY, token).apply()
                Timber.i(response.toString())
                emit(SignUpState.SuccessResponse(response.message))
            }else{
                emit(SignUpState.ErrorResponse(response.message))
            }
        }
    }
}