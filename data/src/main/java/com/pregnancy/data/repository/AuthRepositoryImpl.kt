package com.pregnancy.data.repository

import android.util.Base64
import com.pregnancy.data.source.local.TokenManager
import com.pregnancy.data.source.remote.api.AuthApi
import com.pregnancy.domain.model.User
import com.pregnancy.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi,
    private val tokenManager: TokenManager
) : AuthRepository {
    override suspend fun login(email: String, password: String): Result<User> {
        return try {
            // Create Basic Auth header
            val credentials = "$email:$password"
            val basic = "Basic " + Base64.encodeToString(credentials.toByteArray(), Base64.NO_WRAP)

            val response = api.login(basic)

            if (response.isSuccessful) {
                response.body()?.let { loginResponse ->
                    // Save token
                    tokenManager.saveTokens(loginResponse.data.token)
                    Result.success(loginResponse.data.userDto.toUser())
                } ?: Result.failure(Exception("Empty response body"))
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}