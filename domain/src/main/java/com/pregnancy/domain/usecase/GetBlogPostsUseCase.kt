package com.pregnancy.domain.usecase

import androidx.paging.PagingData
import com.pregnancy.domain.model.PagedData
import com.pregnancy.domain.model.blogpost.BlogPost
import com.pregnancy.domain.repository.BlogRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

// UseCase (Domain Layer)
class GetBlogPostsUseCase @Inject constructor(
    private val blogRepository: BlogRepository
) {
    operator fun invoke(tag: String? = null): Flow<PagingData<BlogPost>> {
        return blogRepository.getBlogPosts(tag)
    }
}
