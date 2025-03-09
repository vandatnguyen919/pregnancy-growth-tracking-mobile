package com.pregnancy.domain.repository

import androidx.paging.PagingData
import com.pregnancy.domain.model.blogpost.BlogPost
import kotlinx.coroutines.flow.Flow

interface BlogRepository {
    fun getBlogPosts(tag: String? = null): Flow<PagingData<BlogPost>>

    suspend fun getBlogPost(id: Long): Result<BlogPost>
}