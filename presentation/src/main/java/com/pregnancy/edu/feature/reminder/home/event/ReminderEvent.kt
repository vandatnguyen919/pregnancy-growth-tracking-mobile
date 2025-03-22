package com.pregnancy.edu.feature.reminder.home.event

import com.pregnancy.edu.common.base.interfaces.ViewEvent
import java.time.LocalDateTime

sealed class ReminderEvent : ViewEvent {

    data class LoadReminders(val reminderDate: LocalDateTime? = null) : ReminderEvent()

    data class CancelReminder(val reminderId: Long) : ReminderEvent()
}