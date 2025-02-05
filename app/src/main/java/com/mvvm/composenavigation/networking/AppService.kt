package com.mvvm.composenavigation.networking

import com.mvvm.composenavigation.feature.notes.create.data.request.CreateNoteRequest
import com.mvvm.composenavigation.feature.notes.create.data.response.CreateNoteResponse
import com.mvvm.composenavigation.networking.NotesApiConstants.NOTES_API
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

import com.mvvm.composenavigation.feature.reminder.data.request.ReminderRequest
import com.mvvm.composenavigation.feature.reminder.data.response.ReminderResponse
import com.mvvm.composenavigation.networking.ReminderApiConstants.REMINDERS_ENDPOINT
import com.mvvm.composenavigation.feature.task.add.data.request.TaskRequest
import com.mvvm.composenavigation.feature.task.add.data.response.TaskResponse
import com.mvvm.composenavigation.networking.TaskApiConstants.SAVE_TASK_URL


interface AppService {
    @POST(REMINDERS_ENDPOINT)
    suspend fun createReminder(
        @Body reminderRequest: ReminderRequest
    ): Response<ReminderResponse>
    @POST(value = SAVE_TASK_URL)
    suspend fun saveTasks(
        @Body request: TaskRequest
    ): Response<TaskResponse>

 /*   @GET(value = "notes")
    suspend fun getNotes(
        @Query("username") username: String,
        @Query("password") password: String,
    ): Response<UserResponse>
*/

    @POST(value = NOTES_API)
    suspend fun createNotes(
        @Body createNoteRequest : CreateNoteRequest
    ): Response<CreateNoteResponse>
}