package com.mvvm.composenavigation.networking

import com.example.task.data.datasource.remote.request.TaskRequest
import com.example.task.data.datasource.remote.response.TaskResponse
import com.mvvm.composenavigation.feature.notes.create.data.request.CreateNoteRequest
import com.mvvm.composenavigation.feature.notes.create.data.response.CreateNoteResponse
import com.mvvm.composenavigation.feature.notes.create.data.response.NoteResponse
import com.mvvm.composenavigation.networking.NotesApiConstants.NOTES_API
import com.mvvm.composenavigation.networking.TaskApiConstants.SAVE_TASK_URL
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AppService {

    @POST(value = SAVE_TASK_URL)
    suspend fun saveTasks(
        @Body request: TaskRequest,
    ): Response<TaskResponse>

    @GET(value = "notes")
    suspend fun getNotes(): Response<List<NoteResponse>>

    @POST(value = NOTES_API)
    suspend fun createNotes(
        @Body createNoteRequest: CreateNoteRequest,
    ): Response<CreateNoteResponse>
}