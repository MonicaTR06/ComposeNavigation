package com.mvvm.composenavigation.networking

import com.mvvm.composenavigation.feature.notes.create.data.request.CreateNoteRequest
import com.mvvm.composenavigation.feature.notes.create.data.response.CreateNoteResponse
import com.mvvm.composenavigation.networking.NotesApiConstants.NOTES_API
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AppService {

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