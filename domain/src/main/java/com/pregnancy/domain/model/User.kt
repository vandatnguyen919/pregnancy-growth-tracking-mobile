package com.pregnancy.domain.model

data class User(
    val id: Long,
    val fullName: String? = null,
    val email: String? = null,
    val username: String? = null,
    val enabled: Boolean? = false,
    val role: String? = null
)