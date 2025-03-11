package com.pregnancy.domain.usecase.blogpost

import androidx.paging.PagingData
import com.pregnancy.domain.model.blogpost.BlogPost
import com.pregnancy.domain.repository.BlogRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

// UseCase (Domain Layer)
class GetBlogPostsUseCase @Inject constructor(
    private val blogRepository: BlogRepository
) {
    operator fun invoke(tag: String? = null): Flow<PagingData<BlogPost>> {
        return blogRepository.getBlogPosts(tag)
    }
}
