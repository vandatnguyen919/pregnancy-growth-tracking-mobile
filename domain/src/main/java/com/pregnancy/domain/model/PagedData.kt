package com.pregnancy.domain.model

data class PagedData<T>(
    val content: List<T>,
    val pageNumber: Int,
    val pageSize: Int,
    val totalPages: Int,
    val totalElements: Int,
    val isLastPage: Boolean,
    val isFirstPage: Boolean
)