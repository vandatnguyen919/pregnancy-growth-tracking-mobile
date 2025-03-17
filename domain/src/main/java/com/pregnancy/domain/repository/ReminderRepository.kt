package com.pregnancy.domain.repository

import androidx.paging.PagingData
import com.pregnancy.domain.model.reminder.Reminder
import kotlinx.coroutines.flow.Flow

interface ReminderRepository {

    fun getReminders(): Flow<PagingData<Reminder>>

//    suspend fun getReminderById(id: Long): Reminder?

    suspend fun scheduleReminder(reminder: Reminder): Result<Reminder>

    suspend fun cancelReminder(reminderId: Long): Result<Unit>

//    suspend fun updateReminder(reminder: Reminder): Boolean
//
//    suspend fun deleteReminder(id: Long): Boolean
}