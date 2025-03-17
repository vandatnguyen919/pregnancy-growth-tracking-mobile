package com.pregnancy.domain.usecase.reminder

import com.pregnancy.domain.model.reminder.Reminder
import com.pregnancy.domain.repository.ReminderRepository
import javax.inject.Inject

class ScheduleReminderUseCase @Inject constructor(
    private val reminderRepository: ReminderRepository
) {
    suspend operator fun invoke(reminder: Reminder) : Result<Reminder> {
        return reminderRepository.scheduleReminder(reminder)
    }
}