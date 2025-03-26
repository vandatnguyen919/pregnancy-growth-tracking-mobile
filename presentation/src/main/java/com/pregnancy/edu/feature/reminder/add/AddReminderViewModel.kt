package com.pregnancy.edu.feature.reminder.add

import android.annotation.SuppressLint
import androidx.lifecycle.viewModelScope
import com.pregnancy.domain.model.reminder.Reminder
import com.pregnancy.domain.usecase.reminder.ScheduleReminderUseCase
import com.pregnancy.edu.common.base.viewmodel.BaseViewModel
import com.pregnancy.edu.feature.reminder.add.event.AddReminderEvent
import com.pregnancy.edu.feature.reminder.add.state.AddReminderState
import com.pregnancy.edu.feature.reminder.add.state.AddReminderViewModelState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.ZoneId
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class AddReminderViewModel @Inject constructor(
    private val scheduleReminderUseCase: ScheduleReminderUseCase,
) : BaseViewModel<AddReminderEvent, AddReminderState, AddReminderViewModelState>(
    initState = AddReminderViewModelState(),
) {

    override fun onTriggerEvent(event: AddReminderEvent) {
        when (event) {
            is AddReminderEvent.UpdateTitle -> updateTitle(event.title)
            is AddReminderEvent.UpdateDescription -> updateDescription(event.description)
            is AddReminderEvent.UpdateReminderType -> updateReminderType(event.reminderType)
            is AddReminderEvent.UpdateDate -> updateDate(event.date)
            is AddReminderEvent.UpdateTime -> updateTime(event.time)
            is AddReminderEvent.SaveReminder -> saveReminder()
        }
    }

    private fun updateTitle(title: String) {

        viewModelState.update {
            it.copy(
                title = title,
                titleError = if (title.isBlank()) "Title is required" else null
            )
        }
    }

    private fun updateDescription(description: String) {
        viewModelState.update { it.copy(description = description) }
    }

    private fun updateReminderType(reminderType: String) {
        viewModelState.update { it.copy(selectedReminderType = reminderType) }
    }

    private fun updateDate(date: Calendar) {
        viewModelState.update { it.copy(selectedDate = date) }
    }

    private fun updateTime(time: Calendar) {
        viewModelState.update { it.copy(selectedTime = time) }
    }

    @SuppressLint("NewApi")
    private fun saveReminder() {
        viewModelState.update { it.copy(isLoading = true) }

        val reminder = Reminder(
            title = viewModelState.value.title,
            description = viewModelState.value.description,
            reminderType = viewModelState.value.selectedReminderType,
            reminderDate = Calendar.getInstance().apply {
                set(
                    viewModelState.value.selectedDate.get(Calendar.YEAR),
                    viewModelState.value.selectedDate.get(Calendar.MONTH),
                    viewModelState.value.selectedDate.get(Calendar.DAY_OF_MONTH),
                    viewModelState.value.selectedTime.get(Calendar.HOUR_OF_DAY),
                    viewModelState.value.selectedTime.get(Calendar.MINUTE)
                )
            }.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
        )

        viewModelScope.launch(Dispatchers.IO) {
            scheduleReminderUseCase(
                reminder = reminder
            ).onSuccess {
                viewModelState.update { it.copy(isLoading = true, addSuccess = true) }
            }.onFailure {
                viewModelState.update { it.copy(isLoading = false, error = it.error) }
            }
        }
    }

    override fun <T> Result<T>.reduce(event: AddReminderEvent) {
        TODO("Not yet implemented")
    }
}