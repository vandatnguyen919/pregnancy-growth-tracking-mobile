package com.pregnancy.data.source.remote.model.blogpost

import com.pregnancy.data.source.remote.model.authentication.UserDto
import com.pregnancy.domain.model.blogpost.BlogPost

// Models
data class BlogPostDto(
    val id: Long,
    val heading: String?,
    val content: String,
    val pageTitle: String,
    val shortDescription: String,
    val featuredImageUrl: String,
    val isVisible: Boolean,
    val commentQuantity: Int,
    val likeQuantity: Int,
    val nameTags: List<String>,
    val userDto: UserDto?
)