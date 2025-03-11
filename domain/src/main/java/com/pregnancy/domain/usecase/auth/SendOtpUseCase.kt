package com.pregnancy.domain.usecase.auth

import com.pregnancy.domain.repository.AuthRepository
import javax.inject.Inject

class SendOtpUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String): Result<Any> {
        return authRepository.sendOtp(email)
    }
}