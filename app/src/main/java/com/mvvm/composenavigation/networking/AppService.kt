package com.mvvm.composenavigation.networking

import com.mvvm.composenavigation.feature.notes.create.data.request.CreateNoteRequest
import com.mvvm.composenavigation.feature.notes.create.data.response.CreateNoteResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AppService {

    @POST(value = "notes")
    suspend fun createNotes(
        @Body createNoteRequest : CreateNoteRequest
    ): Response<CreateNoteResponse>
}