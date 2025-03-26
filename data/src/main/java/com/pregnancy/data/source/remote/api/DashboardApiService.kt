package com.pregnancy.data.source.remote.api

import com.pregnancy.data.source.remote.ApiConstants
import com.pregnancy.data.source.remote.model.ApiResponse
import com.pregnancy.data.source.remote.model.MetricDto
import com.pregnancy.domain.model.dashboard.ChartResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DashboardApiService {

    @GET("${ApiConstants.DASHBOARD_PATH}/column")
    suspend fun getColumnChart(
        @Query("fetusId") fetusId: Long,
        @Query("metricId") metricId: Long,
        @Query("week") week: Int,
    ): Response<ApiResponse<ChartResponse>>

    @GET("${ApiConstants.METRIC_PATH}/week/{week}")
    suspend fun getMetricsByWeek(
        @Path("week") week: Int
    ): Response<ApiResponse<List<MetricDto>>>
}