// Step 1: Add the Hilt Work dependency in your build.gradle
// implementation "androidx.hilt:hilt-work:1.0.0"

// Step 2: Update your ReminderWorker
package com.pregnancy.data.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.pregnancy.data.R
import com.pregnancy.domain.repository.ReminderRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class ReminderWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted params: WorkerParameters,
    private val reminderRepository: ReminderRepository
) : CoroutineWorker(context, params) {

    companion object {
        const val KEY_REMINDER_ID = "reminder_id"
        const val KEY_REMINDER_TITLE = "reminder_title"
        const val KEY_REMINDER_DESCRIPTION = "reminder_description"

        private const val CHANNEL_ID = "reminder_notification_channel"
        private const val CHANNEL_NAME = "Pregnancy Reminders"
    }

    override suspend fun doWork(): Result {
        val reminderIdStr = inputData.getString(KEY_REMINDER_ID) ?: return Result.failure()
        val reminderId = reminderIdStr.toLongOrNull() ?: return Result.failure()
        val title = inputData.getString(KEY_REMINDER_TITLE) ?: "Reminder"
        val description = inputData.getString(KEY_REMINDER_DESCRIPTION) ?: ""

        // Display notification
        createNotificationChannel()

        val notificationId = reminderId.hashCode()
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notifications)
            .setContentTitle(title)
            .setContentText(description)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager
        notificationManager.notify(notificationId, notification)

        // Delete the reminder from API after showing notification
        val result = reminderRepository.cancelReminder(reminderId)
        return if (result.isSuccess) {
            Result.success()
        } else {
            Result.failure()
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance).apply {
                description = "Pregnancy app reminder notifications"
            }

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE)
                    as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}