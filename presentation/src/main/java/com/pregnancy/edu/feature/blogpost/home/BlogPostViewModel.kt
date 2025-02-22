package com.pregnancy.edu.feature.blogpost.home

import com.pregnancy.edu.common.base.viewmodel.BaseViewModel
import com.pregnancy.edu.feature.blogpost.home.event.BlogPostEvent
import com.pregnancy.edu.feature.blogpost.home.state.BlogPostState
import com.pregnancy.edu.feature.blogpost.home.state.BlogPostViewModelState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class BlogPostViewModel @Inject constructor(

) : BaseViewModel<BlogPostEvent, BlogPostState, BlogPostViewModelState>(
    initState = BlogPostViewModelState()
) {
    init {
        loadBlogPosts()
    }

    private fun loadBlogPosts() {
        val blogPosts = BlogPostFactory.createBlogPosts()
        val featuredBlogPosts = blogPosts.take(1)
        val mediumBlogPosts = blogPosts.filter { !featuredBlogPosts.contains(it) }.take(4)
        val compactBlogPosts = blogPosts.filter { !featuredBlogPosts.contains(it) && !mediumBlogPosts.contains(it) }

        viewModelState.update {
            it.copy(
                isLoading = false,
                featuredBlogPosts = featuredBlogPosts,
                mediumBlogPosts = mediumBlogPosts,
                compactBlogPosts = compactBlogPosts,
                error = null
            )
        }
    }

    override fun onTriggerEvent(event: BlogPostEvent) {
        when (event) {
            is BlogPostEvent.LoadBlogPosts -> {
                loadBlogPosts()
            }
        }
    }

    override fun <T> Result<T>.reduce(event: BlogPostEvent) {
        TODO("Not yet implemented")
    }
}