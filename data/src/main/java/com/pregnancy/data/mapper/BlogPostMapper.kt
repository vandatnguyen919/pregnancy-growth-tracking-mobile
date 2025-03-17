package com.pregnancy.data.mapper

import com.pregnancy.data.source.remote.model.blogpost.BlogPostDto
import com.pregnancy.domain.model.blogpost.BlogPost

fun BlogPostDto.toDomain(): BlogPost {
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