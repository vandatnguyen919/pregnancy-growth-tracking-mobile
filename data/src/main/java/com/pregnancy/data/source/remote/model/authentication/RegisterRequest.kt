package com.pregnancy.data.source.remote.model.authentication

data class RegisterRequest(
    val fullName: String,
    val email: String,
    val password: String,
    val confirmPassword: String
)
