package com.pregnancy.data.repository

import android.util.Base64
import com.google.gson.Gson
import com.pregnancy.data.source.local.TokenManager
import com.pregnancy.data.source.remote.api.AuthApi
import com.pregnancy.data.source.remote.model.ErrorResponse
import com.pregnancy.data.source.remote.model.authentication.RegisterRequest
import com.pregnancy.domain.model.authentication.User
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
                response.body()?.let { res ->
                    // Save token
                    tokenManager.saveTokens(res.data.token)
                    Result.success(res.data.userDto.toDomain())
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
            val response = api.register(
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

    override suspend fun sendOtp(email: String): Result<Any> {
        return try {
            val response = api.generateOtp(email)

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
            val response = api.validateEmail(email, otp)

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

    private fun parseErrorResponse(errorBody: String?): String {
        return try {
            errorBody?.let {
                val errorResponse = Gson().fromJson(it, ErrorResponse::class.java)
                // Return the most specific error message available
                errorResponse.data ?: errorResponse.message
            } ?: "Unknown error occurred"
        } catch (e: Exception) {
            "Failed to parse error response"
        }
    }
}