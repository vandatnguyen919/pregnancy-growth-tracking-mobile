package com.pregnancy.domain.usecase.auth

import com.pregnancy.domain.model.authentication.User
import com.pregnancy.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        fullName: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Result<User> {
        return authRepository.register(fullName, email, password, confirmPassword)
    }
}