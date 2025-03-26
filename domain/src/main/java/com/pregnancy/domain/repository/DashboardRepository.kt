package com.pregnancy.domain.repository

import com.pregnancy.domain.model.dashboard.ChartResponse
import com.pregnancy.domain.model.pregnancy.Pregnancy

interface DashboardRepository {

    suspend fun getGestationalWeekCharts(fetusId: Long, week: Int): Result<List<ChartResponse>>
}