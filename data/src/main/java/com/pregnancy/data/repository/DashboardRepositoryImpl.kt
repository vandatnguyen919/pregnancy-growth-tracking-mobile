package com.pregnancy.data.repository

import com.pregnancy.data.source.remote.api.DashboardApiService
import com.pregnancy.data.source.remote.api.PregnancyApiService
import com.pregnancy.domain.model.dashboard.ChartResponse
import com.pregnancy.domain.repository.DashboardRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class DashboardRepositoryImpl @Inject constructor(
    private val apiService: DashboardApiService
) : DashboardRepository {

    override suspend fun getGestationalWeekCharts(
        fetusId: Long,
        week: Int
    ): Result<List<ChartResponse>> {
        return try {
            val response = apiService.getMetricsByWeek(week)
            val metrics = response.body()?.data ?: emptyList()

            if (metrics.isEmpty()) {
                return Result.failure(Exception("No metrics found for week $week"))
            }

            // Make all API calls in parallel using coroutines
            val deferredResults = metrics.map { metric ->
                coroutineScope {
                    async {
                        apiService.getColumnChart(fetusId, metric.id, week)
                    }
                }
            }

            // Await all results
            val results = deferredResults.awaitAll()

            // Process results
            val chartResponses = results.mapNotNull { response ->
                if (response.isSuccessful) {
                    response.body()?.data
                } else {
                    null
                }
            }

            if (chartResponses.isEmpty()) {
                Result.failure(Exception("Failed to fetch any chart data"))
            } else {
                Result.success(chartResponses)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}