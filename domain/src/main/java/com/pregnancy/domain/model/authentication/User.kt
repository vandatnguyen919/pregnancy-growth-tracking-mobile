package com.pregnancy.domain.model.authentication

import com.pregnancy.domain.model.BloodType
import java.time.LocalDateTime

data class User(
    val id: Long,
    val email: String,
    val username: String,
    val phoneNumber: String?,
    val fullName: String?,
    val dateOfBirth: LocalDateTime?,
    val avatarUrl: String?,
    val gender: Boolean?,
    val bloodType: BloodType?,
    val symptoms: String?,
    val nationality: String?,
    val createdAt: LocalDateTime?,
    val verified: Boolean = false,
    val enabled: Boolean = false,
    val role: String
)