package com.pregnancy.data.source.remote.api

import com.pregnancy.data.ApiConstants
import com.pregnancy.data.source.remote.model.ApiResponse
import com.pregnancy.data.source.remote.model.authentication.LoginResponse
import com.pregnancy.data.source.remote.model.authentication.RegisterRequest
import com.pregnancy.data.source.remote.model.authentication.UserDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {

    @POST("${ApiConstants.AUTH_PATH}/login")
    suspend fun login(
        @Header("Authorization") authHeader: String
    ): Response<ApiResponse<LoginResponse>>

    @POST("${ApiConstants.AUTH_PATH}/register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): Response<ApiResponse<UserDto>>
}