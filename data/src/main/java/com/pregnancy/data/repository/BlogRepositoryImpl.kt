package com.pregnancy.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.pregnancy.data.mapper.toDomain
import com.pregnancy.data.source.remote.ENABLE_PLACEHOLDERS
import com.pregnancy.data.source.remote.INITIAL_LOAD_SIZE_HINT
import com.pregnancy.data.source.remote.MAX_SIZE
import com.pregnancy.data.source.remote.PAGE_SIZE
import com.pregnancy.data.source.remote.PREFETCH_DISTANCE
import com.pregnancy.data.source.remote.api.BlogApiService
import com.pregnancy.data.source.remote.paging.BlogPagingSource
import com.pregnancy.data.source.remote.parseErrorResponse
import com.pregnancy.domain.model.blogpost.BlogPost
import com.pregnancy.domain.repository.BlogRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BlogRepositoryImpl(
    private val apiService: BlogApiService,
) : BlogRepository {

    override fun getBlogPosts(tag: String?): Flow<PagingData<BlogPost>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                prefetchDistance = PREFETCH_DISTANCE,
                enablePlaceholders = ENABLE_PLACEHOLDERS,
                initialLoadSize = INITIAL_LOAD_SIZE_HINT,
                maxSize = MAX_SIZE
            ),
            pagingSourceFactory = {
                BlogPagingSource(apiService, tag)
            }
        ).flow.map { pagingData ->
            pagingData.map { blogPostDto ->
                blogPostDto.toDomain()
            }
        }
    }

    override suspend fun getBlogPost(id: Long): Result<BlogPost> {
        return try {
            val response = apiService.getBlogPost(id)
            if (response.isSuccessful) {
                response.body()?.let { res ->
                    Result.success(res.data.toDomain())
                } ?: Result.failure(Exception("Empty response body"))
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = parseErrorResponse(errorBody)
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}