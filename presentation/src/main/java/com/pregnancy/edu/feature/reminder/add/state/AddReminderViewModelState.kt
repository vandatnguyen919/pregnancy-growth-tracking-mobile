package com.pregnancy.edu.feature.reminder.add.state

import com.pregnancy.edu.common.base.interfaces.ViewModelState
import com.pregnancy.edu.common.base.interfaces.ViewState
import com.pregnancy.edu.feature.reminder.add.ReminderType
import java.util.Calendar

data class AddReminderViewModelState(
    val title: String = "",
    val titleError: String? = null,
    val description: String = "",
    val selectedReminderType: String? = ReminderType.APPOINTMENT.displayName,
    val selectedDate: Calendar = Calendar.getInstance(),
    val selectedTime: Calendar = Calendar.getInstance(),
    val isLoading: Boolean = false,
    val error: String? = null
) : ViewModelState() {

    override fun toUiState(): ViewState {
        return AddReminderState(
            title = title,
            titleError = titleError,
            description = description,
            selectedReminderType = selectedReminderType,
            selectedDate = selectedDate,
            selectedTime = selectedTime,
            isLoading = isLoading,
            error = error
        )
    }
}