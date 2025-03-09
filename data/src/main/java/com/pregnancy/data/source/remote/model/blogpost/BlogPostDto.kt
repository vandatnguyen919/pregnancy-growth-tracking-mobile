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
) {
    fun toDomain(): BlogPost {
        return BlogPost(
            id = id,
            heading = heading,
            content = content,
            pageTitle = pageTitle,
            shortDescription = shortDescription,
            featuredImageUrl = featuredImageUrl,
            isVisible = isVisible,
            commentQuantity = commentQuantity,
            likeQuantity = likeQuantity,
            nameTags = nameTags,
            user = userDto?.toDomain()
        )
    }
}