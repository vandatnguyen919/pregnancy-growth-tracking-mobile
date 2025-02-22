package com.pregnancy.data.source.remote.model.authentication

import com.google.gson.annotations.SerializedName
import com.pregnancy.domain.model.authentication.User

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
    val role: String
) {
    fun toUser() = User(
        id = id,
        fullName = fullName,
        email = email,
        username = username,
        enabled = enabled,
        role = role
    )
}
