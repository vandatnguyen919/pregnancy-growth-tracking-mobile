package com.pregnancy.edu.feature.blogpost.home.state

import com.pregnancy.domain.model.blogpost.BlogPost
import com.pregnancy.edu.common.base.interfaces.ViewState

data class BlogPostState(
    val isLoading: Boolean = false,
    val featuredBlogPosts: List<BlogPost> = emptyList(),
    val mediumBlogPosts: List<BlogPost> = emptyList(),
    val compactBlogPosts: List<BlogPost> = emptyList(),
    val error: String? = null
) : ViewState() {
}