package com.pregnancy.edu.feature.blogpost.detail

import androidx.lifecycle.viewModelScope
import com.pregnancy.domain.usecase.blogpost.GetBlogPostUseCase
import com.pregnancy.edu.common.base.viewmodel.BaseViewModel
import com.pregnancy.edu.feature.blogpost.detail.event.BlogPostDetailEvent
import com.pregnancy.edu.feature.blogpost.detail.state.BlogPostDetailState
import com.pregnancy.edu.feature.blogpost.detail.state.BlogPostDetailViewModelState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BlogPostDetailViewModel @Inject constructor(
    private val getBlogPostUseCase: GetBlogPostUseCase
) : BaseViewModel<BlogPostDetailEvent, BlogPostDetailState, BlogPostDetailViewModelState>(
    initState = BlogPostDetailViewModelState()
) {

    override fun onTriggerEvent(event: BlogPostDetailEvent) {
        when (event) {
            is BlogPostDetailEvent.LoadBlogPost -> {
                loadBlogPost(event.blogPostId)
            }
        }
    }

    private fun loadBlogPost(blogPostId: Long) {
        viewModelState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val result = getBlogPostUseCase(blogPostId)
            result.onSuccess { blogPost ->
                viewModelState.update { it.copy(blogPost = blogPost, isLoading = false, error = null) }
            }.onFailure { error ->
                viewModelState.update { it.copy(isLoading = false, error = error.localizedMessage ?: "Unknown error") }
            }
        }
    }

    override fun <T> Result<T>.reduce(event: BlogPostDetailEvent) {
        TODO("Not yet implemented")
    }
}