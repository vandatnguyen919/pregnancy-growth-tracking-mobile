package com.pregnancy.data.source.remote.interceptor

import com.pregnancy.data.source.local.TokenManager
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val token = runBlocking { tokenManager.getAccessToken() }
        val request = chain.request().newBuilder()
        
        token?.let {
            request.addHeader("Authorization", "Bearer $it")
        }
        
        return chain.proceed(request.build())
    }
}