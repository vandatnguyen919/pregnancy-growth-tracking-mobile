package com.pregnancy.edu.feature.reminder.home

import android.annotation.SuppressLint
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.pregnancy.domain.model.reminder.Reminder
import com.pregnancy.domain.usecase.reminder.CancelReminderUseCase
import com.pregnancy.domain.usecase.reminder.GetRemindersUseCase
import com.pregnancy.edu.common.base.viewmodel.BaseViewModel
import com.pregnancy.edu.feature.reminder.home.event.ReminderEvent
import com.pregnancy.edu.feature.reminder.home.state.ReminderState
import com.pregnancy.edu.feature.reminder.home.state.ReminderViewModelState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class ReminderViewModel @Inject constructor(
    private val getRemindersUseCase: GetRemindersUseCase,
    private val cancelReminderUseCase: CancelReminderUseCase,
) : BaseViewModel<ReminderEvent, ReminderState, ReminderViewModelState>(
    initState = ReminderViewModelState(),
) {

    val pagingDataFlow = viewModelState.map { it.pagingData }.distinctUntilChanged()

    @SuppressLint("NewApi")
    override fun onTriggerEvent(event: ReminderEvent) {
        when (event) {
            is ReminderEvent.LoadReminders -> {
                loadReminders(event.reminderDate)
            }
            is ReminderEvent.CancelReminder -> {
                cancelReminder(event.reminderId)
            }
        }
    }

    private fun cancelReminder(reminderId: Long) {
        viewModelState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            cancelReminderUseCase(
                reminderId = reminderId
            ).onSuccess {
                viewModelState.update { it.copy(isLoading = true) }
            }.onFailure {
                viewModelState.update { it.copy(isLoading = false, error = it.error) }
            }
        }
    }

    private fun loadReminders(localDateTime: LocalDateTime? = null) {
        viewModelState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            try {
                val remindersFlow: Flow<PagingData<Reminder>> =
                    getRemindersUseCase(localDateTime).cachedIn(viewModelScope)

                remindersFlow.collectLatest { pagingData ->
                    viewModelState.update {
                        it.copy(
                            pagingData = pagingData,
                            isLoading = false,
                            error = null
                        )
                    }
                }
            } catch (e: Exception) {
                viewModelState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message
                    )
                }
            }
        }
    }

    override fun <T> Result<T>.reduce(event: ReminderEvent) {
        TODO("Not yet implemented")
    }
}