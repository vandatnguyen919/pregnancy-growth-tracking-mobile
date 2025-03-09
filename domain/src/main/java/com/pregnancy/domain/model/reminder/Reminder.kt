package com.pregnancy.domain.model.reminder

data class Reminder(
    val id : Long,
    val description: String? = null,
    val reminderType: String? = null,
    val reminderDate: String? = null,
    val status: String? = null,
) {
}