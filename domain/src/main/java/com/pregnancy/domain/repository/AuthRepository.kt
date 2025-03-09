package com.pregnancy.domain.repository

import com.pregnancy.domain.model.authentication.User

interface AuthRepository {

    suspend fun login(email: String, password: String): Result<User>

    suspend fun register(fullName: String, email: String, password: String, confirmPassword: String): Result<User>

    suspend fun sendOtp(email: String): Result<Any>

    suspend fun validateEmail(email: String, otp: String): Result<Any>
}