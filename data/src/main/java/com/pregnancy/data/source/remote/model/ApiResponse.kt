package com.pregnancy.data.source.remote.model

data class ApiResponse<T>(
    val flag: Boolean,
    val code: Int,
    val message: String,
    val data: T
)