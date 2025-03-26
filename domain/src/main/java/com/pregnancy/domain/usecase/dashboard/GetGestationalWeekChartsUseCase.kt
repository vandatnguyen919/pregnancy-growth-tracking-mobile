package com.pregnancy.domain.usecase.dashboard

import com.pregnancy.domain.model.dashboard.ChartResponse
import com.pregnancy.domain.model.pregnancy.Fetus
import com.pregnancy.domain.repository.DashboardRepository
import com.pregnancy.domain.repository.PregnancyRepository
import javax.inject.Inject

class GetGestationalWeekChartsUseCase @Inject constructor(
    private val dashboardRepository: DashboardRepository
) {
    suspend operator fun invoke(fetusId: Long, week: Int): Result<List<ChartResponse>> {
        return dashboardRepository.getGestationalWeekCharts(
            fetusId = fetusId,
            week = week
        )
    }
}