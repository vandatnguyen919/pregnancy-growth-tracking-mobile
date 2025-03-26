package com.pregnancy.domain.model.dashboard

class ChartData(
    val type: String, // "max", "value", or "min"
    val name: String, // Week identifier
    val value: Double
) {
}