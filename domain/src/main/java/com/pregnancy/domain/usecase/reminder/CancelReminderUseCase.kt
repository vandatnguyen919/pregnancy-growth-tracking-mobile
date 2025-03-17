package com.pregnancy.domain.usecase.reminder

import com.pregnancy.domain.repository.ReminderRepository
import javax.inject.Inject

class CancelReminderUseCase @Inject constructor(
    private val reminderRepository: ReminderRepository
) {
    suspend operator fun invoke(reminderId: Long): Result<Unit> {
        return reminderRepository.cancelReminder(reminderId)
    }
}