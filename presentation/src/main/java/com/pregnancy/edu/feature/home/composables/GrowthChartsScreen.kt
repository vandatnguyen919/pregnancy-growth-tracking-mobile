package com.pregnancy.edu.feature.home.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pregnancy.edu.common.theme.Pink500
import com.pregnancy.edu.common.theme.Pink700
import com.pregnancy.edu.feature.home.DashboardViewModel

@Composable
fun GrowthChartsScreen(
    fetusId: Long,
    week: Int,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    
    LaunchedEffect(Unit) {
        viewModel.onTriggerEvent(
            DashboardViewModel.DashboardEvent.LoadGestationalWeekCharts(fetusId, week)
        )
    }

    when {
        state.isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Pink500)
            }
        }
        state.error != null -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = "Error: ${state.error}",
                    color = Pink700
                )
            }
        }
        state.charts.isEmpty() -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = "No growth data available for this week",
                    color = Pink700
                )
            }
        }
        else -> {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                state.charts.forEachIndexed { index, chartResponse ->
                    GrowthMetricChart(
                        chartResponse = chartResponse,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = if (index < state.charts.size - 1) 16.dp else 0.dp)
                    )
                }
            }
        }
    }
}