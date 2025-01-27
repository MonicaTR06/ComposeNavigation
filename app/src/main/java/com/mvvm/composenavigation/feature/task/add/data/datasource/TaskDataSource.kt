package com.mvvm.composenavigation.feature.task.add.data.request

import com.google.gson.Gson
import com.mvvm.composenavigation.feature.task.add.data.response.TaskResponse
import com.mvvm.composenavigation.networking.AppService
import com.mvvm.composenavigation.networking.RetrofitInstance
import com.mvvm.composenavigation.networking.model.ErrorResponse
import com.mvvm.composenavigation.networking.model.ServiceError
import com.mvvm.composenavigation.networking.model.ServiceResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException

class TaskDataSource(
    private val service: AppService = RetrofitInstance.appService,
    private val gson: Gson = Gson()
) {
    fun createTask(
        request: TaskRequest
    ): Flow<ServiceResult<TaskResponse>> = flow {
        try {
            val response = service.saveTasks(request)
            if (response.isSuccessful) {
                val taskResponse = response.body() as TaskResponse
                emit(ServiceResult.Success(taskResponse))
            } else {
                when (response.code()) {
                    500 -> {
                        val body = response.errorBody()?.string()
                        val errorResponse = gson.fromJson(body, ErrorResponse::class.java)
                        emit(ServiceResult.Error(ServiceError.ServerError(errorResponse)))
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