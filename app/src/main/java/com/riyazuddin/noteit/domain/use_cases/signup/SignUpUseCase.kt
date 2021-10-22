package com.riyazuddin.noteit.domain.use_cases.signup

import android.content.SharedPreferences
import com.riyazuddin.noteit.common.Constants
import com.riyazuddin.noteit.common.Constants.NO_TOKEN
import com.riyazuddin.noteit.common.Constants.VALID
import com.riyazuddin.noteit.common.Validator
import com.riyazuddin.noteit.domain.repository.IAuthRepository
import com.riyazuddin.noteit.presentation.signup.SignUpState
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
            if (emailResult != VALID)
                return@flow emit(SignUpState.EmailError(emailResult))

            val passwordResult = Validator.validatePassword(password)
            if (passwordResult != VALID)
                return@flow emit(SignUpState.PasswordError(passwordResult))

            val repeatedPasswordResult = Validator.validateRepeatPassword(password, repeatPassword)
            if (repeatedPasswordResult != VALID)
                return@flow emit(SignUpState.RepeatPasswordError(repeatedPasswordResult))

            emit(SignUpState.ProcessingSignUpEvent(true))

            val response = repository.signUp(email, password)
            if (response.successful) {
                val token = response.token ?: NO_TOKEN
                sharedPreferences.edit().putString(Constants.TOKEN_KEY, token).apply()
                Timber.i(response.toString())
                return@flow emit(SignUpState.SuccessResponse(response.message))
            }else{
                return@flow emit(SignUpState.ErrorResponse(response.message))
            }
        }
    }
}