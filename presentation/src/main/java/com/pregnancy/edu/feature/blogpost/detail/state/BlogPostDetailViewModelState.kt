package com.pregnancy.edu.feature.blogpost.detail.state

import com.pregnancy.domain.model.blogpost.BlogPost
import com.pregnancy.edu.common.base.interfaces.ViewModelState
import com.pregnancy.edu.common.base.interfaces.ViewState

data class BlogPostDetailViewModelState(
    val isLoading: Boolean = false,
    val blogPost: BlogPost? = null,
    val error: String? = null
) : ViewModelState(){
    override fun toUiState(): ViewState {
        return BlogPostDetailState(
            isLoading = isLoading,
            blogPost = blogPost,
            error = error
        )
    }
}