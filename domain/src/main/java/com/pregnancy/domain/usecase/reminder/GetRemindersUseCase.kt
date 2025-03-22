package com.pregnancy.domain.usecase.reminder

import androidx.paging.PagingData
import com.pregnancy.domain.model.reminder.Reminder
import com.pregnancy.domain.repository.ReminderRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import javax.inject.Inject

class GetRemindersUseCase @Inject constructor(
    private val reminderRepository: ReminderRepository
) {

    operator fun invoke(reminderDate: LocalDateTime? = null): Flow<PagingData<Reminder>> {
        return reminderRepository.getReminders(reminderDate)
    }
}