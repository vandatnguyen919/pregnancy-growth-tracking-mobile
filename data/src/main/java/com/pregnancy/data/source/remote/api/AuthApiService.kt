package com.pregnancy.data.source.remote.api

import com.pregnancy.data.source.remote.ApiConstants
import com.pregnancy.data.source.remote.model.ApiResponse
import com.pregnancy.data.source.remote.model.authentication.LoginResponse
import com.pregnancy.data.source.remote.model.authentication.RegisterRequest
import com.pregnancy.data.source.remote.model.authentication.UserDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthApiService {

    @POST("${ApiConstants.AUTH_PATH}/login")
    suspend fun login(
        @Header("Authorization") authHeader: String
    ): Response<ApiResponse<LoginResponse>>

    @POST("${ApiConstants.AUTH_PATH}/register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): Response<ApiResponse<UserDto>>

    @GET("${ApiConstants.AUTH_PATH}/profile")
    suspend fun getMyProfile(): Response<ApiResponse<UserDto>>

    @POST("${ApiConstants.OTP_PATH}/generate")
    suspend fun generateOtp(
        @Query("email") email: String
    ): Response<ApiResponse<Any>>

    @POST("${ApiConstants.OTP_PATH}/validate-email")
    suspend fun validateEmail(
        @Query("email") email: String,
        @Query("otp") otp: String
    ): Response<ApiResponse<Any>>
}