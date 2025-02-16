package com.pregnancy.domain.repository

import com.pregnancy.domain.model.User

interface AuthRepository {

    suspend fun login(email: String, password: String): Result<User>
}