package com.example.reminder.data.datasource.remote.service

import com.example.reminder.data.datasource.remote.request.ReminderRequest
import com.example.reminder.data.datasource.remote.response.ReminderResponse
import com.example.reminder.data.datasource.remote.service.ApiURL.REMINDERS_ENDPOINT
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface ReminderService {
    @POST(REMINDERS_ENDPOINT)
    suspend fun createReminder(
        @Body reminderRequest: ReminderRequest
    ): Response<ReminderResponse>
}