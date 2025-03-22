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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BlogPostViewModel @Inject constructor(
    private val getBlogPostsUseCase: GetBlogPostsUseCase
) : BaseViewModel<BlogPostEvent, BlogPostState, BlogPostViewModelState>(
    initState = BlogPostViewModelState()
) {
    val pagingDataFlow = viewModelState.map { it.pagingData }.distinctUntilChanged()

    init {
        loadBlogPosts()
    }

    private fun loadBlogPosts() {
        viewModelState.update { it.copy(isLoading = true) }

        viewModelScope.launch(Dispatchers.IO){
            try {
                val blogPostsFlow: Flow<PagingData<BlogPost>> =
                    getBlogPostsUseCase().cachedIn(viewModelScope)

                blogPostsFlow.collectLatest { pagingData ->
                    viewModelState.update {
                        it.copy(
                            pagingData = pagingData,
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