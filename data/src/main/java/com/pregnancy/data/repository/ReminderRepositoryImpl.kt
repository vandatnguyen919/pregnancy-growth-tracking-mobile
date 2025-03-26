package com.pregnancy.data.repository

import android.annotation.SuppressLint
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.pregnancy.data.mapper.toDomain
import com.pregnancy.data.mapper.toDto
import com.pregnancy.data.source.remote.ENABLE_PLACEHOLDERS
import com.pregnancy.data.source.remote.INITIAL_LOAD_SIZE_HINT
import com.pregnancy.data.source.remote.MAX_SIZE
import com.pregnancy.data.source.remote.PAGE_SIZE
import com.pregnancy.data.source.remote.PREFETCH_DISTANCE
import com.pregnancy.data.source.remote.api.ReminderApiService
import com.pregnancy.data.source.remote.paging.ReminderPagingSource
import com.pregnancy.data.source.remote.parseErrorResponse
import com.pregnancy.data.worker.ReminderWorker
import com.pregnancy.domain.model.reminder.Reminder
import com.pregnancy.domain.repository.ReminderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ReminderRepositoryImpl @Inject constructor(
    private val apiService: ReminderApiService,
    private val workManager: WorkManager,
) : ReminderRepository {

    // Keep a reference to the latest paging source
    private var currentPagingSource: ReminderPagingSource? = null

    override fun getReminders(reminderDate: LocalDateTime?): Flow<PagingData<Reminder>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                prefetchDistance = PREFETCH_DISTANCE,
                enablePlaceholders = ENABLE_PLACEHOLDERS,
                initialLoadSize = INITIAL_LOAD_SIZE_HINT,
                maxSize = MAX_SIZE
            ),
            pagingSourceFactory = {
                ReminderPagingSource(
                    reminderDate = reminderDate,
                    apiService = apiService
                ).also { currentPagingSource = it }
            }
        ).flow.map { pagingData ->
            pagingData.map { reminderDto ->
                reminderDto.toDomain()
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun scheduleReminder(reminder: Reminder): Result<Reminder> {
        return try {
            val response = apiService.addReminder(reminder.toDto())

            if (response.isSuccessful) {
                response.body()?.let { res ->
                    val savedReminder = res.data.toDomain()
                    scheduleReminderNotification(savedReminder)
                    // Invalidate the paging source to trigger a refresh
                    currentPagingSource?.invalidate()
                    Result.success(savedReminder)
                } ?: Result.failure(Exception("Empty response body"))
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = parseErrorResponse(errorBody)
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    @SuppressLint("NewApi")
    private fun scheduleReminderNotification(reminder: Reminder): Result<Unit> {
        return try {
            val data = Data.Builder()
                .putString(ReminderWorker.KEY_REMINDER_ID, reminder.id.toString())
                .putString(ReminderWorker.KEY_REMINDER_TITLE, reminder.title)
                .putString(ReminderWorker.KEY_REMINDER_DESCRIPTION, reminder.description)
                .build()

            val currentTime = System.currentTimeMillis()
            val reminderTimeMillis = reminder.reminderDate.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
            val delayInMillis = reminderTimeMillis - currentTime

            val safeDelayInMillis = maxOf(0, delayInMillis)

            val oneTimeWorkRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
                .setInitialDelay(safeDelayInMillis, TimeUnit.MILLISECONDS)
                .setInputData(data)
                .build()

            // Enqueue the work with a unique identifier
            workManager.enqueueUniqueWork(
                reminder.id.toString(),
                ExistingWorkPolicy.REPLACE,
                oneTimeWorkRequest
            )

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun cancelReminder(reminderId: Long): Result<Unit> {
        return try {
            val response = apiService.deleteReminder(reminderId)

            if (response.isSuccessful) {
                workManager.cancelUniqueWork(reminderId.toString())
                // Invalidate the paging source to trigger a refresh
                currentPagingSource?.invalidate()
                Result.success(Unit)
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = parseErrorResponse(errorBody)
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}