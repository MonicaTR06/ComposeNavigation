package com.mvvm.composenavigation.feature.task.add.data.request

import com.demo.network.data.mapper.toDomain
import com.demo.network.data.model.ErrorResponse
import com.demo.network.data.retrofit.RetrofitInstance
import com.demo.network.domain.model.ServiceError
import com.demo.network.domain.model.ServiceResult
import com.example.task.data.datasource.remote.request.TaskRequest
import com.example.task.data.datasource.remote.response.TaskResponse
import com.example.task.data.datasource.remote.service.TaskService
import com.example.task.domain.model.Task
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException

class TaskDataSource(
    private val taskService: TaskService = RetrofitInstance.createService<TaskService>(),
    private val gson: Gson = Gson()
) {
    fun createTask(
        request: Task
    ): Flow<ServiceResult<TaskResponse>> = flow {
        try {
            val response = taskService.saveTasks(TaskRequest())
            if (response.isSuccessful) {
                val taskResponse = response.body() as TaskResponse
                emit(ServiceResult.Success(taskResponse))
            } else {
                when (response.code()) {
                    500 -> {
                        val body = response.errorBody()?.string()
                        val errorResponse = gson.fromJson(body, ErrorResponse::class.java)
                        emit(ServiceResult.Error(ServiceError.ServerError(errorResponse.toDomain())))
                    }

                    else -> {
                        emit(ServiceResult.Error(ServiceError.Unexpected))
                    }
                }
            }
        } catch (ex: IOException) {
            emit(ServiceResult.Error(ServiceError.NetworkError))
        } catch (ex: Exception) {
            emit(ServiceResult.Error(ServiceError.Unexpected))
        }
    }.flowOn(Dispatchers.IO)
}