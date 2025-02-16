package com.pregnancy.data.source.remote.api

import com.pregnancy.data.ApiConstants
import com.pregnancy.data.source.remote.model.ApiResponse
import com.pregnancy.data.source.remote.model.authentication.LoginResponse
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {

    @POST("${ApiConstants.AUTH_PATH}/login")
    suspend fun login(
        @Header("Authorization") authHeader: String
    ): Response<ApiResponse<LoginResponse>>
}