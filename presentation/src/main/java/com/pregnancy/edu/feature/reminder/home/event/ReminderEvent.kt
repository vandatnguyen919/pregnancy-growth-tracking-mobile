package com.pregnancy.edu.feature.reminder.home.event

import com.pregnancy.edu.common.base.interfaces.ViewEvent

sealed class ReminderEvent : ViewEvent {

    data object LoadReminders : ReminderEvent()

    data object ReloadReminders: ReminderEvent()

    data class CancelReminder(val reminderId: Long) : ReminderEvent()
}