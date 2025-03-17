package com.pregnancy.data.mapper

import com.pregnancy.data.source.remote.model.reminder.ReminderDto
import com.pregnancy.domain.model.reminder.Reminder

fun ReminderDto.toDomain(): Reminder {
    return Reminder(
        id = id,
        title = title,
        description = description,
        reminderType = reminderType,
        reminderDate = reminderDate,
        status = status
    )
}

fun Reminder.toDto(): ReminderDto {
    return ReminderDto(
        id = id,
        title = title ?: "",
        description = description ?: "",
        reminderType = reminderType ?: "",
        reminderDate = reminderDate,
        status = status
    )
}