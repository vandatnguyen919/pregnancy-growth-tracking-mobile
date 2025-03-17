package com.pregnancy.data.source.remote

import com.google.gson.Gson
import com.pregnancy.data.source.remote.model.ErrorResponse

fun parseErrorResponse(errorBody: String?): String {
    return try {
        errorBody?.let {
            val errorResponse = Gson().fromJson(it, ErrorResponse::class.java)
            // Return the most specific error message available
            errorResponse.data ?: errorResponse.message
        } ?: "Unknown error occurred"
    } catch (e: Exception) {
        "Failed to parse error response"
    }
}