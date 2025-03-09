package com.pregnancy.domain.usecase

import com.pregnancy.domain.model.blogpost.BlogPost
import com.pregnancy.domain.repository.BlogRepository
import javax.inject.Inject

class GetBlogPostUseCase @Inject constructor(
    private val blogRepository: BlogRepository
) {
    suspend operator fun invoke(id: Long) : Result<BlogPost> {
        return blogRepository.getBlogPost(id)
    }
}