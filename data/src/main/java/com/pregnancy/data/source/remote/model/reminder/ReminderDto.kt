package com.pregnancy.data.source.remote.model.reminder

import com.pregnancy.domain.model.ReminderStatus
import com.pregnancy.domain.model.reminder.Reminder
import java.time.LocalDateTime

data class ReminderDto(
    val id: Long? = null,
    val title: String,
    val description: String,
    val reminderType: String,
    val reminderDate: LocalDateTime,
    val status: ReminderStatus? = null,
)