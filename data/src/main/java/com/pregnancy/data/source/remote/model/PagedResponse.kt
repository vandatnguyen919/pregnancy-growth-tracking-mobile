package com.pregnancy.data.source.remote.model

import com.google.gson.annotations.SerializedName
import com.pregnancy.data.R
import com.pregnancy.domain.model.PagedData

data class PagedResponse<T>(
    val content: List<T>,
    val pageNumber: Int,
    val pageSize: Int,
    val totalPages: Int,
    val totalElements: Int,

    @SerializedName("last")
    val isLast: Boolean,

    @SerializedName("first")
    val isFirst: Boolean
) {
    fun <R> toDomain(mapper: (T) -> R): PagedData<R> {
        return PagedData(
            content = content.map(mapper),
            pageNumber = pageNumber,
            pageSize = pageSize,
            totalPages = totalPages,
            totalElements = totalElements,
            isLastPage = isLast,
            isFirstPage = isFirst
        )
    }
}