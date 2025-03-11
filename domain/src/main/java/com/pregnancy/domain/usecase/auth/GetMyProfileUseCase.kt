package com.pregnancy.domain.usecase.auth

import com.pregnancy.domain.model.authentication.User
import com.pregnancy.domain.repository.AuthRepository
import javax.inject.Inject


class GetMyProfileUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Result<User> {
        return authRepository.getMyProfile()
    }
}