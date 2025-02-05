package com.demo.network.domain.model

sealed interface ServiceResult<out T> {
    data class Success<T>(val data: T) : ServiceResult<T>
    data class Error(val serviceError: ServiceError) : ServiceResult<Nothing>
    data object Loading : ServiceResult<Nothing>
}

sealed class ServiceError {
    data class ServerError(val errorMessage: ErrorMessage) : ServiceError()
    data object NetworkError : ServiceError()
    data object Unexpected : ServiceError()
}