package com.mvvm.composenavigation.networking

import com.example.task.data.datasource.remote.request.TaskRequest
import com.example.task.data.datasource.remote.response.TaskResponse
import com.mvvm.composenavigation.feature.notes.create.data.request.CreateNoteRequest
import com.mvvm.composenavigation.feature.notes.create.data.response.CreateNoteResponse
import com.mvvm.composenavigation.feature.reminder.data.request.ReminderRequest
import com.mvvm.composenavigation.feature.reminder.data.response.ReminderResponse
import com.mvvm.composenavigation.networking.NotesApiConstants.NOTES_API
import com.mvvm.composenavigation.networking.ReminderApiConstants.REMINDERS_ENDPOINT
import com.mvvm.composenavigation.networking.TaskApiConstants.SAVE_TASK_URL
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface AppService {
    @POST(REMINDERS_ENDPOINT)
    suspend fun createReminder(
        @Body reminderRequest: ReminderRequest
    ): Response<ReminderResponse>

    @POST(value = NOTES_API)
    suspend fun createNotes(
        @Body createNoteRequest : CreateNoteRequest
    ): Response<CreateNoteResponse>
}