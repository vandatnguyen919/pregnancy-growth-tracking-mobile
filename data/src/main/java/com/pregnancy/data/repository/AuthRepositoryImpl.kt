package com.pregnancy.data.repository

import android.util.Base64
import com.pregnancy.data.source.local.TokenManager
import com.pregnancy.data.source.remote.api.AuthApiService
import com.pregnancy.data.source.remote.model.authentication.RegisterRequest
import com.pregnancy.data.source.remote.parseErrorResponse
import com.pregnancy.domain.model.authentication.User
import com.pregnancy.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: AuthApiService,
    private val tokenManager: TokenManager
) : AuthRepository {

    override suspend fun login(email: String, password: String): Result<User> {
        return try {
            // Create Basic Auth header
            val credentials = "$email:$password"
            val basic = "Basic " + Base64.encodeToString(credentials.toByteArray(), Base64.NO_WRAP)

            val response = apiService.login(basic)

            if (response.isSuccessful) {
                response.body()?.let { res ->
                    // Save token
                    tokenManager.saveTokens(res.data.token)
                    Result.success(res.data.userDto.toDomain())
                    getMyProfile()
                } ?: Result.failure(Exception("Empty response body"))
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = parseErrorResponse(errorBody)
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun register(
        fullName: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Result<User> {
        return try {
            val response = apiService.register(
                RegisterRequest(
                    fullName = fullName,
                    email = email,
                    password = password,
                    confirmPassword = confirmPassword
                )
            )

            if (response.isSuccessful) {
                response.body()?.let { res ->
                    Result.success(res.data.toDomain())
                } ?: Result.failure(Exception("Empty response body"))
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = parseErrorResponse(errorBody)
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMyProfile(): Result<User> {
        return try {
            val response = apiService.getMyProfile()

            if (response.isSuccessful) {
                response.body()?.let { res ->
                    Result.success(res.data.toDomain())
                } ?: Result.failure(Exception("Empty response body"))
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = parseErrorResponse(errorBody)
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun sendOtp(email: String): Result<Any> {
        return try {
            val response = apiService.generateOtp(email)

            if (response.isSuccessful) {
                Result.success(response.body()?.data ?: Any())
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = parseErrorResponse(errorBody)
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun validateEmail(email: String, otp: String): Result<Any> {
        return try {
            val response = apiService.validateEmail(email, otp)

            if (response.isSuccessful) {
                Result.success(response.body()?.data ?: Any())
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = parseErrorResponse(errorBody)
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}