package com.example.task.data.datasource.remote.service

import com.example.task.data.datasource.remote.request.TaskRequest
import com.example.task.data.datasource.remote.response.TaskResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Query

interface TaskService {

    @GET(value = TaskApiConstants.SAVE_TASK_URL)
    suspend fun validateTask(
        @Query("title") title: String,
        @Query("priority") priority: Boolean,
    ): Response<TaskResponse>

    suspend fun saveTasks(
        @Body request: TaskRequest
    ): Response<TaskResponse>
}