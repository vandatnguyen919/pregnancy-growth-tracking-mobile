package com.pregnancy.edu.feature.reminder.add.state

import com.pregnancy.edu.common.base.interfaces.ViewState
import java.util.Calendar

data class AddReminderState(
    val title: String = "",
    val titleError: String? = null,
    val description: String = "",
    val selectedReminderType: String? = null,
    val selectedDate: Calendar = Calendar.getInstance(),
    val selectedTime: Calendar = Calendar.getInstance(),
    val isLoading: Boolean = false,
    val error: String? = null
) : ViewState() {

    fun isFormValid(): Boolean {
        return title.isNotBlank()
    }
}