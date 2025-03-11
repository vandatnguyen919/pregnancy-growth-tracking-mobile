package com.pregnancy.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.google.gson.Gson
import com.pregnancy.data.source.remote.api.BlogApiService
import com.pregnancy.data.source.remote.model.ErrorResponse
import com.pregnancy.data.source.remote.model.authentication.RegisterRequest
import com.pregnancy.data.source.remote.paging.BlogPagingSource
import com.pregnancy.data.source.remote.parseErrorResponse
import com.pregnancy.domain.model.blogpost.BlogPost
import com.pregnancy.domain.repository.BlogRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BlogRepositoryImpl(
    private val apiService: BlogApiService,
) : BlogRepository {

    companion object {
        // Number of items per page
        const val PAGE_SIZE: Int = 8

        // Number of items to prefetch
        const val PREFETCH_DISTANCE: Int = 1

        // Whether to display placeholders when actual data is not yet loaded
        const val ENABLE_PLACEHOLDERS: Boolean = false

        // Number of items to load for the first page
        const val INITIAL_LOAD_SIZE_HINT: Int = 8

        // Maximum number of items that Paging will keep in memory
        // Standard equation: maxSize = pageSize + (2 * prefetchDistance)
        const val MAX_SIZE: Int = PAGE_SIZE + (2 * PREFETCH_DISTANCE)
    }

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