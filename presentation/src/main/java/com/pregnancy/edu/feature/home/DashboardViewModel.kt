package com.pregnancy.edu.feature.home

import androidx.lifecycle.viewModelScope
import com.pregnancy.domain.model.dashboard.ChartResponse
import com.pregnancy.domain.usecase.dashboard.GetGestationalWeekChartsUseCase
import com.pregnancy.edu.common.base.interfaces.ViewEvent
import com.pregnancy.edu.common.base.interfaces.ViewModelState
import com.pregnancy.edu.common.base.interfaces.ViewState
import com.pregnancy.edu.common.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getGestationalWeekChartsUseCase: GetGestationalWeekChartsUseCase
): BaseViewModel<DashboardViewModel.DashboardEvent, DashboardViewModel.DashboardState, DashboardViewModel.DashboardViewModelState>(
    initState = DashboardViewModelState()
) {
    override fun onTriggerEvent(event: DashboardEvent) {
        when (event) {
            is DashboardEvent.LoadGestationalWeekCharts -> loadGestationalWeekCharts(event.fetusId, event.week)
        }
    }

    private fun loadGestationalWeekCharts(fetusId: Long, week: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getGestationalWeekChartsUseCase(fetusId, week).onSuccess { charts ->
                viewModelState.update { it.copy(charts = charts) }
            }.onFailure { error ->
                viewModelState.update { it.copy(error = error.message) }
            }
        }
    }

    sealed class DashboardEvent : ViewEvent {
        data class LoadGestationalWeekCharts(val fetusId: Long, val week: Int) : DashboardEvent()
    }

    data class DashboardState(
        val charts: List<ChartResponse> = emptyList(),
        val isLoading: Boolean = false,
        val error: String? = null
    ) : ViewState()

    data class DashboardViewModelState(
        val charts: List<ChartResponse> = emptyList(),
        val isLoading: Boolean = false,
        val error: String? = null
    ) : ViewModelState() {

        override fun toUiState(): ViewState {
            return DashboardState(
                charts = charts,
                isLoading = isLoading,
                error = error
            )
        }
    }

    override fun <T> Result<T>.reduce(event: DashboardEvent) {
        TODO("Not yet implemented")
    }
}