package com.demo.login.data.datasource.remote

import com.demo.network.data.mapper.toDomain
import com.demo.login.data.datasource.remote.response.UserResponse
import com.demo.login.data.datasource.remote.service.LoginService
import com.demo.network.data.model.ErrorResponse
import com.demo.network.data.retrofit.RetrofitInstance
import com.demo.network.domain.model.ServiceError
import com.demo.network.domain.model.ServiceResult
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException

class LoginDataSource(
    private val loginService: LoginService = RetrofitInstance.createService<LoginService>(),
    private val gson: Gson = Gson()
) {

    fun validateUser(username: String, password: String): Flow<ServiceResult<UserResponse>> = flow {
        try {
            val response = loginService.validateUser(username, password)
            if (response.isSuccessful) {
                val userResponse = response.body() as UserResponse
                emit(ServiceResult.Success(userResponse))
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