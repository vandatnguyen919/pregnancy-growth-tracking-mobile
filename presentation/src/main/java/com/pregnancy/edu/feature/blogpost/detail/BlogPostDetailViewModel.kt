package com.pregnancy.edu.feature.blogpost.detail

import androidx.lifecycle.viewModelScope
import com.pregnancy.edu.common.base.viewmodel.BaseViewModel
import com.pregnancy.edu.feature.blogpost.detail.event.BlogPostDetailEvent
import com.pregnancy.edu.feature.blogpost.detail.state.BlogPostDetailState
import com.pregnancy.edu.feature.blogpost.detail.state.BlogPostDetailViewModelState
import com.pregnancy.edu.feature.blogpost.home.BlogPostFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class BlogPostDetailViewModel @Inject constructor(

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
        viewModelScope.launch(Dispatchers.IO) {
            viewModelState.update { it ->
//                it.copy(blogPost = getBlogPostUseCase(blogPostId))
                val blogPost = BlogPostFactory.createBlogPosts().find { it.id == blogPostId }
                it.copy(blogPost = blogPost)
            }
        }
    }

    override fun <T> Result<T>.reduce(event: BlogPostDetailEvent) {
        TODO("Not yet implemented")
    }
}