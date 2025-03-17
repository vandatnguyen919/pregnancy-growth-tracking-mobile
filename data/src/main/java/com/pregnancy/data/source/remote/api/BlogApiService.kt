package com.pregnancy.data.source.remote.api

import com.pregnancy.data.source.remote.ApiConstants
import com.pregnancy.data.source.remote.model.ApiResponse
import com.pregnancy.data.source.remote.model.PagedResponse
import com.pregnancy.data.source.remote.model.blogpost.BlogPostDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// API Service
interface BlogApiService {

    @GET(ApiConstants.BLOG_POST_PATH)
    suspend fun getBlogPosts(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<ApiResponse<PagedResponse<BlogPostDto>>>

    @GET("${ApiConstants.BLOG_POST_PATH}/{id}")
    suspend fun getBlogPost(
        @Path("id") id: Long
    ): Response<ApiResponse<BlogPostDto>>
}