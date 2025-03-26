import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.barchart.BarChart
import co.yml.charts.ui.barchart.models.*
import com.pregnancy.edu.feature.home.DashboardViewModel

@Composable
fun PregnancyBarChart(
    fetusId: Long,
    week: Int,
    modifier: Modifier = Modifier,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.onTriggerEvent(
            DashboardViewModel.DashboardEvent.LoadGestationalWeekCharts(fetusId, week)
        )
    }

    // Define pregnancy-friendly colors
    val maxColor = Color(0xFFF8BBD0) // Light pink
    val valueColor = Color(0xFF64B5F6) // Soft blue
    val minColor = Color(0xFFA5D6A7) // Soft green
    val backgroundColor = Color(0xFFFAFAFA) // Off-white
    val gridColor = Color(0xFFEEEEEE) // Light gray
    val textColor = Color(0xFF616161) // Dark gray
    val titleColor = Color(0xFF5C6BC0) // Purple blue

    // Create bar data with correct Point constructor
    val barData = listOf(
        BarData(
            point = Point(-1f, 0f),  // Dummy point for spacing
            color = Color.Transparent,
            label = "",
            description = ""
        ),
        BarData(
            point = Point(0f, 5.5f),  // x-axis position, y-axis value
            color = maxColor,
            label = "Maximum",
            description = "Maximum range for Week 10 is 5.5 cm"
        ),
        BarData(
            point = Point(1f, 4.43f),
            color = valueColor,
            label = "Your Baby",
            description = "Your baby's size at Week 10 is 4.43 cm"
        ),
        BarData(
            point = Point(2f, 3f),
            color = minColor,
            label = "Minimum",
            description = "Minimum range for Week 10 is 3.0 cm"
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Week 10 Growth Measurements",
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
            color = titleColor,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "Baby size comparison (in cm)",
            fontSize = 14.sp,
            color = Color(0xFF9E9E9E),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(containerColor = backgroundColor)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                // X-Axis configuration
                val xAxisData = AxisData.Builder()
                    .axisStepSize(100.dp)  // Ensure enough space between bars
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
                        val value = i * (6f / 5)
                        String.format("%.1f", value)
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

                // Create the BarChartData with correct constructor
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
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Legend explanation
        Text(
            text = "Measurements show your baby's growth is within the normal range",
            fontSize = 14.sp,
            color = Color(0xFF757575),
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}