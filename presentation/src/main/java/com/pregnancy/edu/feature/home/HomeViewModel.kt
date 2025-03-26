package com.pregnancy.edu.feature.home

import android.annotation.SuppressLint
import androidx.lifecycle.viewModelScope
import com.pregnancy.domain.usecase.dashboard.GetGestationalWeekChartsUseCase
import com.pregnancy.domain.usecase.pregnancy.GetGestationalWeekInsightUseCase
import com.pregnancy.domain.usecase.pregnancy.GetMyFetusesUseCase
import com.pregnancy.domain.usecase.pregnancy.GetOnGoingPregnancyUseCase
import com.pregnancy.edu.common.base.viewmodel.BaseViewModel
import com.pregnancy.edu.feature.home.event.HomeEvent
import com.pregnancy.edu.feature.home.state.HomeState
import com.pregnancy.edu.feature.home.state.HomeViewModelState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getOnGoingPregnancyUseCase: GetOnGoingPregnancyUseCase,
    private val getGestationalWeekInsightUseCase: GetGestationalWeekInsightUseCase,
    private val getMyFetusesUseCase: GetMyFetusesUseCase,
    private val getGestationalWeekChartsUseCase: GetGestationalWeekChartsUseCase,
) : BaseViewModel<HomeEvent, HomeState, HomeViewModelState>(
    initState = HomeViewModelState(),
) {
    init {
        loadMyOnGoingPregnancy()
        loadGestationalWeekInsight()
        loadMyFetuses()
    }

    override fun onTriggerEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.LoadMyOnGoingPregnancy -> loadMyOnGoingPregnancy()
            is HomeEvent.LoadGestationalWeekInsight -> loadGestationalWeekInsight()
            is HomeEvent.LoadMyFetuses ->  loadMyFetuses()
            is HomeEvent.LoadGestationalWeekCharts -> loadGestationalWeekCharts(event.fetusId, event.week)
        }
    }

    private fun loadMyOnGoingPregnancy() {
        viewModelState.update { it.copy(isLoading = true) }

        viewModelScope.launch(Dispatchers.IO) {
            getOnGoingPregnancyUseCase().onSuccess { pregnancy ->
                viewModelState.update { it.copy(pregnancy = pregnancy, isLoading = false) }
                // Recalculate progress when pregnancy data is loaded
                calculatePregnancyProgress()
            }.onFailure { error ->
                viewModelState.update { it.copy(error = error.message, isLoading = false) }
            }
        }
    }

    private fun loadGestationalWeekInsight() {
        viewModelScope.launch(Dispatchers.IO) {
            getGestationalWeekInsightUseCase().onSuccess { insight ->
                viewModelState.update { it.copy(gestationalWeekInsight = insight) }
            }.onFailure { error ->
                viewModelState.update { it.copy(error = error.message) }
            }
        }
    }

    private fun loadMyFetuses() {
        viewModelScope.launch(Dispatchers.IO) {
            getMyFetusesUseCase().onSuccess { fetuses ->
                viewModelState.update { it.copy(fetuses = fetuses) }
            }.onFailure { error ->
                viewModelState.update { it.copy(error = error.message) }
            }
        }
    }

    private fun loadGestationalWeekCharts(fetusId: Long, week: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getGestationalWeekChartsUseCase(fetusId, week).onSuccess { charts ->
                viewModelState.update { it.copy(gestationalWeekCharts = charts) }
            }.onFailure { error ->
                viewModelState.update { it.copy(error = error.message) }
            }
        }
    }

    @SuppressLint("NewApi")
    private fun calculatePregnancyProgress() {
        // Use the pregnancy due date if available, otherwise use a default date
        val dueDate = viewModelState.value.pregnancy?.expectedDueDate

        val currentDate = LocalDate.now()

        // Calculate conception date (40 weeks before due date)
        val conceptionDate = dueDate?.minusWeeks(40)

        // Calculate weeks pregnant
        val weeksPregnant = ChronoUnit.WEEKS.between(conceptionDate, currentDate).toInt()

        // Determine trimester
        val trimester = when {
            weeksPregnant < 13 -> "First"
            weeksPregnant < 27 -> "Second"
            else -> "Third"
        }

        // Calculate remaining days
        val remainingDays = ChronoUnit.DAYS.between(currentDate, dueDate).toInt()

        viewModelState.update {
            it.copy(
                expectedDueDate = dueDate,
                currentDate = currentDate,
                weeksPregnant = weeksPregnant,
                trimester = trimester,
                remainingDays = remainingDays
            )
        }
    }

    override fun <T> Result<T>.reduce(event: HomeEvent) {
        TODO("Not yet implemented")
    }
}