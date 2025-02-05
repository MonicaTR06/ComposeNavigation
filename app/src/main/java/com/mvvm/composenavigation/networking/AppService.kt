package com.mvvm.composenavigation.networking

import com.mvvm.composenavigation.feature.task.add.data.request.TaskRequest
import com.mvvm.composenavigation.feature.task.add.data.response.TaskResponse
import com.mvvm.composenavigation.networking.TaskApiConstants.SAVE_TASK_URL
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AppService {
    @POST(value = SAVE_TASK_URL)
    suspend fun saveTasks(
        @Body request: TaskRequest
    ): Response<TaskResponse>

}