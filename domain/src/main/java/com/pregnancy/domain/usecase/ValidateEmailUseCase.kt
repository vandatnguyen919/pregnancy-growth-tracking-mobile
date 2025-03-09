package com.pregnancy.domain.usecase

import com.pregnancy.domain.repository.AuthRepository
import javax.inject.Inject

class ValidateEmailUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, otp: String): Result<Any> {
        return authRepository.validateEmail(email, otp)
    }
}