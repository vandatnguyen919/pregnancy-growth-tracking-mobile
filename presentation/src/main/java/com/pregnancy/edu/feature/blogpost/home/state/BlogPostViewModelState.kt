package com.pregnancy.edu.feature.blogpost.home.state

import androidx.paging.PagingData
import com.pregnancy.domain.model.blogpost.BlogPost
import com.pregnancy.edu.common.base.interfaces.ViewModelState
import com.pregnancy.edu.common.base.interfaces.ViewState

data class BlogPostViewModelState(
    val pagingData: PagingData<BlogPost> = PagingData.empty(),
    val isLoading: Boolean = false,
    val error: String? = null
) : ViewModelState() {

    override fun toUiState(): ViewState {
        return BlogPostState(
            pagingData = pagingData,
            isLoading = isLoading,
            error = error
        )
    }
}