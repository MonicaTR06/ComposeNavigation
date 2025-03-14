package com.example.task.data.datasource.remote.service

import com.example.task.data.datasource.remote.request.TaskRequest
import com.example.task.data.datasource.remote.response.SaveTaskResponse
import com.example.task.data.datasource.remote.response.TaskItemResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TaskService {

    @POST(value = TaskApiConstants.SAVE_TASK_URL)
    suspend fun saveTasks(
        @Body request: TaskRequest
    ): Response<SaveTaskResponse>

    @GET(value = "tasks")
    suspend fun getTasks(): Response<List<TaskItemResponse>>
}