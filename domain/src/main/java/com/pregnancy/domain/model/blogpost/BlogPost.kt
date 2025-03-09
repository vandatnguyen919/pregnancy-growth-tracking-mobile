package com.pregnancy.domain.model.blogpost

import com.pregnancy.domain.model.authentication.User

// Models
data class BlogPost(
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
    val user: User?
)