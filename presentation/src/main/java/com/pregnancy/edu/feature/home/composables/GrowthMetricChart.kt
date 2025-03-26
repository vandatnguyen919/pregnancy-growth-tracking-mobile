package com.pregnancy.edu.feature.home.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.barchart.BarChart
import co.yml.charts.ui.barchart.models.BarChartData
import co.yml.charts.ui.barchart.models.BarChartType
import co.yml.charts.ui.barchart.models.BarData
import co.yml.charts.ui.barchart.models.BarStyle
import com.pregnancy.domain.model.dashboard.ChartResponse
import com.pregnancy.edu.common.theme.Pink700
import java.util.Locale

@Composable
fun GrowthMetricChart(
    chartResponse: ChartResponse,
    modifier: Modifier = Modifier
) {
    // Define pregnancy-friendly colors
    val maxColor = Color(0xFFF8BBD0) // Light pink
    val valueColor = Color(0xFF64B5F6) // Soft blue
    val minColor = Color(0xFFA5D6A7) // Soft green
    val backgroundColor = Color(0xFFFAFAFA) // Off-white
    val gridColor = Color(0xFFEEEEEE) // Light gray
    val textColor = Color(0xFF616161) // Dark gray
    val titleColor = Color(0xFF5C6BC0) // Purple blue

    // Extract data from the response
    val metric = chartResponse.metric
    val chartData = chartResponse.data

    // Find the week from the data (assuming all have same week)
    val week = chartData.firstOrNull()?.name ?: "Unknown Week"

    // Create bar data
    val barData = listOf(
        // Spacer bar for visual padding
        BarData(
            point = Point(-1f, 0f),
            color = Color.Transparent,
            label = "",
            description = ""
        )
    ) + chartData.mapIndexed { index, measurementData ->
        BarData(
            point = Point(index.toFloat(), measurementData.value.toFloat()),
            color = when (measurementData.type) {
                "max" -> maxColor
                "value" -> valueColor
                "min" -> minColor
                else -> valueColor
            },
            label = when (measurementData.type) {
                "max" -> "Maximum"
                "value" -> "Your Baby"
                "min" -> "Minimum"
                else -> measurementData.type
            },
            description = "${measurementData.type} ${metric.name} for ${measurementData.name} is ${measurementData.value} ${metric.unit}"
        )
    }

    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = metric.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = titleColor
            )
            
            Text(
                text = "$week (${metric.unit})",
                fontSize = 14.sp,
                color = Pink700,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                // Find max value for Y-axis scaling
                val maxValue = chartData.maxOfOrNull { it.value }?.toFloat() ?: 100f
                val roundedMax = kotlin.math.ceil(maxValue / 10) * 10 // Round up to nearest 10

                // X-Axis configuration
                val xAxisData = AxisData.Builder()
                    .axisStepSize(100.dp)
                    .steps(barData.size - 1)
                    .labelData { i -> barData[i].label }
                    .labelAndAxisLinePadding(15.dp)
                    .axisLineColor(gridColor)
                    .axisLabelColor(textColor)
                    .build()

                // Y-Axis configuration
                val yAxisData = AxisData.Builder()
                    .steps(5)
                    .labelData { i ->
                        val value = i * (roundedMax / 5)
                        String.format(Locale.getDefault(), "%.1f", value)
                    }
                    .labelAndAxisLinePadding(15.dp)
                    .axisLineColor(gridColor)
                    .axisLabelColor(textColor)
                    .build()

                // Bar style
                val barStyle = BarStyle(
                    barWidth = 50.dp,
                    cornerRadius = 8.dp
                )

                // Create the BarChartData
                val barChartData = BarChartData(
                    chartData = barData,
                    xAxisData = xAxisData,
                    yAxisData = yAxisData,
                    backgroundColor = backgroundColor,
                    barStyle = barStyle,
                    horizontalExtraSpace = 16.dp,
                    paddingEnd = 16.dp,
                    barChartType = BarChartType.VERTICAL
                )

                BarChart(
                    modifier = Modifier.fillMaxSize(),
                    barChartData = barChartData
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))

            // Status message
            val currentValue = chartData.find { it.type == "value" }?.value ?: 0.0
            val minValue = chartData.find { it.type == "min" }?.value ?: 0.0
            val maxValue = chartData.find { it.type == "max" }?.value ?: 0.0

            val statusMessage = when {
                currentValue in minValue..maxValue ->
                    "Your baby's ${metric.name.lowercase()} is within normal range"
                currentValue > maxValue ->
                    "Your baby's ${metric.name.lowercase()} is above normal range"
                else ->
                    "Your baby's ${metric.name.lowercase()} is below normal range"
            }

            Text(
                text = statusMessage,
                fontSize = 14.sp,
                color = Pink700,
                textAlign = TextAlign.Center
            )
        }
    }
}