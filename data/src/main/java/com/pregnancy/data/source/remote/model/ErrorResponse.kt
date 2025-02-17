package com.pregnancy.data.source.remote.model

data class ErrorResponse(
    val flag: Boolean,
    val code: Int,
    val message: String,
    val data: String?
)