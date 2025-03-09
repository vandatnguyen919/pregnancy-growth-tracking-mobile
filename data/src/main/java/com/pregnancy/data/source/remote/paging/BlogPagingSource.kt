package com.pregnancy.data.source.remote.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.pregnancy.data.source.remote.api.BlogApiService
import com.pregnancy.data.source.remote.model.blogpost.BlogPostDto
import retrofit2.HttpException
import java.io.IOException

class BlogPagingSource(
    private val apiService: BlogApiService,
    private val tag: String? = null
) : PagingSource<Int, BlogPostDto>() {
    
    override fun getRefreshKey(state: PagingState<Int, BlogPostDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
    
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BlogPostDto> {
        val page = params.key ?: 0
        
        return try {
            val response = apiService.getBlogPosts(
                page = page,
                size = params.loadSize,
//                tag = tag
            )
            
            if (response.isSuccessful) {
                val apiResponse = response.body()
                Log.d("BlogPagingSource", "load: $apiResponse")
                if (apiResponse != null && apiResponse.flag && apiResponse.code == 200) {
                    val pagedData = apiResponse.data
                    val blogPosts = pagedData.content
                    
                    LoadResult.Page(
                        data = blogPosts,
                        prevKey = if (page > 0) page - 1 else null,
                        nextKey = if (pagedData.isLast) null else page + 1
                    )
                } else {
                    LoadResult.Error(Exception(apiResponse?.message ?: "Unknown error"))
                }
            } else {
                LoadResult.Error(HttpException(response))
            }
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}