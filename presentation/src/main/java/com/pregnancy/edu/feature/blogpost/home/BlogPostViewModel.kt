package com.pregnancy.edu.feature.blogpost.home

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.pregnancy.domain.model.blogpost.BlogPost
import com.pregnancy.domain.usecase.blogpost.GetBlogPostsUseCase
import com.pregnancy.edu.common.base.viewmodel.BaseViewModel
import com.pregnancy.edu.feature.blogpost.home.event.BlogPostEvent
import com.pregnancy.edu.feature.blogpost.home.state.BlogPostState
import com.pregnancy.edu.feature.blogpost.home.state.BlogPostViewModelState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BlogPostViewModel @Inject constructor(
    private val getBlogPostsUseCase: GetBlogPostsUseCase
) : BaseViewModel<BlogPostEvent, BlogPostState, BlogPostViewModelState>(
    initState = BlogPostViewModelState()
) {
    private var _pagingDataFlow = MutableStateFlow<PagingData<BlogPost>>(PagingData.empty())
    val pagingDataFlow = _pagingDataFlow.asStateFlow()

    init {
        loadBlogPosts()
    }

    private fun loadBlogPosts() {
        viewModelState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            try {
                val blogPostsFlow: Flow<PagingData<BlogPost>> = getBlogPostsUseCase(
//                    tag = viewModelState.value.selectedTag
                ).cachedIn(viewModelScope)

                blogPostsFlow.collectLatest { pagingData ->
                    _pagingDataFlow.value = pagingData
                    viewModelState.update {
                        it.copy(
                            isLoading = false,
                            error = null
                        )
                    }
                }
            } catch (e: Exception) {
                viewModelState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message
                    )
                }
            }
        }

//        val featuredBlogPosts = blogPosts.take(1)
//        val mediumBlogPosts = blogPosts.filter { !featuredBlogPosts.contains(it) }.take(4)
//        val compactBlogPosts = blogPosts.filter { !featuredBlogPosts.contains(it) && !mediumBlogPosts.contains(it) }
//
//        viewModelState.update {
//            it.copy(
//                isLoading = false,
//                featuredBlogPosts = featuredBlogPosts,
//                mediumBlogPosts = mediumBlogPosts,
//                compactBlogPosts = compactBlogPosts,
//                error = null
//            )
//        }
    }

    override fun onTriggerEvent(event: BlogPostEvent) {
        when (event) {
            is BlogPostEvent.LoadBlogPosts -> {
                loadBlogPosts()
            }

            BlogPostEvent.ClearFilters -> TODO()
            BlogPostEvent.ErrorDismissed -> TODO()
            is BlogPostEvent.FilterByTag -> TODO()
            BlogPostEvent.RefreshBlogPosts -> TODO()
        }
    }

    override fun <T> Result<T>.reduce(event: BlogPostEvent) {
        TODO("Not yet implemented")
    }
}