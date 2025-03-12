package com.mvvm.composenavigation.feature.notes.create.data.datasource

import com.mvvm.composenavigation.feature.notes.create.data.request.CreateNoteRequest
import com.mvvm.composenavigation.networking.RetrofitInstance
import com.mvvm.composenavigation.networking.AppService
import com.mvvm.composenavigation.networking.model.ServiceError
import com.mvvm.composenavigation.networking.model.ServiceResult
import com.mvvm.composenavigation.networking.model.ErrorResponse
import com.google.gson.Gson
import com.mvvm.composenavigation.feature.notes.create.data.response.CreateNoteResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException


class NotesDataSource (
    private val service: AppService = RetrofitInstance.createService<AppService>(),
    private val gson: Gson = Gson()
){

    fun createNotes(request: CreateNoteRequest): Flow<ServiceResult<CreateNoteResponse>> = flow{
        try {
            val response = service.createNotes(request)
            if (response.isSuccessful) {
                val noteResponse = response.body() as CreateNoteResponse
                emit(ServiceResult.Success(noteResponse))
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
        }catch (ex: IOException) {
            emit(ServiceResult.Error(ServiceError.NetworkError))
        } catch (ex: Exception) {
            emit(ServiceResult.Error(ServiceError.Unexpected))
        }
    }.flowOn(Dispatchers.IO)


}