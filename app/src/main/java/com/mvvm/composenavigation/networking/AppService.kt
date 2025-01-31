package com.mvvm.composenavigation.networking

import com.mvvm.composenavigation.feature.reminder.data.request.ReminderRequest
import com.mvvm.composenavigation.feature.reminder.data.response.ReminderResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AppService {
    @POST(value="reminders")
    suspend fun createReminder(
        @Body reminderRequest: ReminderRequest
    ): Response<ReminderResponse>

}