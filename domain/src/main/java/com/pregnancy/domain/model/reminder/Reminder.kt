package com.pregnancy.domain.model.reminder

import com.pregnancy.domain.model.ReminderStatus
import java.time.LocalDateTime

data class Reminder(
    val id : Long? = null,
    val title: String? = null,
    val description: String? = null,
    val reminderType: String? = null,
    val reminderDate: LocalDateTime,
    val status: ReminderStatus? = null,
) {
}