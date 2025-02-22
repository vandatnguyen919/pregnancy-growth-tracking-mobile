package com.pregnancy.edu.feature.blogpost.detail.state

import com.pregnancy.domain.model.blogpost.BlogPost
import com.pregnancy.edu.common.base.interfaces.ViewState

data class BlogPostDetailState(
    val isLoading: Boolean = false,
    val blogPost: BlogPost? = null,
    val error: String? = null
) : ViewState() {
}