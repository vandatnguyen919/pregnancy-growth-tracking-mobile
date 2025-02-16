package com.pregnancy.data.source.remote.model.authentication

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @SerializedName("userInfo")
    val userDto: UserDto,
    @SerializedName("token")
    val token: String
)
