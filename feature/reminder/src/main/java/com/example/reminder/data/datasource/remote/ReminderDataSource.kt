package com.example.reminder.data.datasource.remote

import com.demo.network.data.mapper.toDomain
import com.demo.network.data.model.ErrorResponse
import com.demo.network.data.retrofit.RetrofitInstance
import com.demo.network.domain.model.ServiceError
import com.demo.network.domain.model.ServiceResult
import com.example.reminder.data.datasource.remote.request.ReminderRequest
import com.example.reminder.data.datasource.remote.response.ReminderResponse
import com.example.reminder.data.datasource.remote.response.RemindersResponse
import com.example.reminder.data.datasource.remote.service.ReminderService
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException

class ReminderDataSource(
    private val service: ReminderService = RetrofitInstance.createService<ReminderService>(),
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

    fun getReminders(): Flow<ServiceResult<RemindersResponse>> = flow {
        try {
            val response = service.getReminders()
            if (response.isSuccessful) {
                val responseList = response.body() as List<ReminderResponse>
                emit(ServiceResult.Success(RemindersResponse(responseList)))
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
        }catch (ex: IOException) {
            emit(ServiceResult.Error(ServiceError.NetworkError))
        } catch (ex: Exception) {
            emit(ServiceResult.Error(ServiceError.Unexpected))
        }
    }.flowOn(Dispatchers.IO)
}