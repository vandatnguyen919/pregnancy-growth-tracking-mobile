package com.pregnancy.edu.feature.blogpost.home.state

import com.pregnancy.domain.model.blogpost.BlogPost
import com.pregnancy.edu.common.base.interfaces.ViewModelState
import com.pregnancy.edu.common.base.interfaces.ViewState

data class BlogPostViewModelState(
    val isLoading: Boolean = false,
    val featuredBlogPosts: List<BlogPost> = emptyList(),
    val mediumBlogPosts: List<BlogPost> = emptyList(),
    val compactBlogPosts: List<BlogPost> = emptyList(),
    val error: String? = null
) : ViewModelState() {

    override fun toUiState(): ViewState {
        return BlogPostState(
            isLoading = isLoading,
            featuredBlogPosts = featuredBlogPosts,
            mediumBlogPosts = mediumBlogPosts,
            compactBlogPosts = compactBlogPosts,
            error = error
        )
    }
}