package com.mvvm.composenavigation.feature.reminder.data.datasource

import com.google.gson.Gson
import com.mvvm.composenavigation.feature.reminder.data.request.ReminderRequest
import com.mvvm.composenavigation.feature.reminder.data.response.ReminderResponse
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

class ReminderDataSource(
    private val service: AppService = RetrofitInstance.createService<AppService>(),
    private val gson: Gson = Gson()
) {
    fun createReminder(request: ReminderRequest): Flow<ServiceResult<ReminderResponse>> = flow {
        try {
            val response = service.createReminder(request)
            if (response.isSuccessful) {
                val reminderResponse = response.body() as ReminderResponse
                emit(ServiceResult.Success(reminderResponse))
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