package com.pregnancy.domain.model.dashboard

data class ChartResponse(
    val metric: Metric,
    val data: List<ChartData>,
) {
}