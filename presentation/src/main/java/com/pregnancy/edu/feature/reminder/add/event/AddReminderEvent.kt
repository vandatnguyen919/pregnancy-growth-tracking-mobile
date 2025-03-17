package com.pregnancy.edu.feature.reminder.add.event

import com.pregnancy.edu.common.base.interfaces.ViewEvent
import java.util.Calendar

sealed class AddReminderEvent : ViewEvent {

    data class UpdateTitle(val title: String) : AddReminderEvent()
    data class UpdateDescription(val description: String) : AddReminderEvent()
    data class UpdateReminderType(val reminderType: String) : AddReminderEvent()
    data class UpdateDate(val date: Calendar) : AddReminderEvent()
    data class UpdateTime(val time: Calendar) : AddReminderEvent()

    data object SaveReminder : AddReminderEvent()
}