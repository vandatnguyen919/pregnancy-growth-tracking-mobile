package com.pregnancy.data.source.remote.model.authentication

import com.google.gson.annotations.SerializedName
import com.pregnancy.domain.model.BloodType
import com.pregnancy.domain.model.authentication.User
import java.time.LocalDateTime

data class UserDto(
    @SerializedName("id")
    val id: Long,
    @SerializedName("fullName")
    val fullName: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("enabled")
    val enabled: Boolean,
    @SerializedName("role")
    val role: String,
    @SerializedName("phoneNumber")
    val phoneNumber: String?,
    @SerializedName("dateOfBirth")
    val dateOfBirth: LocalDateTime?,
    @SerializedName("avatarUrl")
    val avatarUrl: String?,
    @SerializedName("gender")
    val gender: Boolean?,
    @SerializedName("bloodType")
    val bloodType: BloodType?,
    @SerializedName("symptoms")
    val symptoms: String?,
    @SerializedName("nationality")
    val nationality: String?,
    @SerializedName("createdAt")
    val createdAt: LocalDateTime?,
    @SerializedName("verified")
    val verified: Boolean
) {
    fun toDomain() = User(
        id = id,
        fullName = fullName,
        email = email,
        username = username,
        enabled = enabled,
        role = role,
        phoneNumber = phoneNumber,
        dateOfBirth = dateOfBirth,
        avatarUrl = avatarUrl,
        gender = gender,
        bloodType = bloodType,
        symptoms = symptoms,
        nationality = nationality,
        createdAt = createdAt,
        verified = verified
    )
}
