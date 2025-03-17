package com.pregnancy.data.source.remote.api

import com.pregnancy.data.source.remote.ApiConstants
import com.pregnancy.data.source.remote.model.ApiResponse
import com.pregnancy.data.source.remote.model.PagedResponse
import com.pregnancy.data.source.remote.model.reminder.ReminderDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ReminderApiService {

    @GET(ApiConstants.REMINDER_PATH)
    suspend fun getReminders(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<ApiResponse<PagedResponse<ReminderDto>>>

    @POST(ApiConstants.REMINDER_PATH)
    suspend fun addReminder(
        @Body reminder: ReminderDto
    ): Response<ApiResponse<ReminderDto>>
}