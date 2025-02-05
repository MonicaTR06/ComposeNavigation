package com.demo.login.data.repository

import com.demo.login.data.datasource.mapper.toDomain
import com.demo.login.data.datasource.remote.LoginDataSource
import com.demo.login.data.datasource.remote.response.UserResponse
import com.demo.login.domain.model.User
import com.demo.login.domain.repository.LoginRepository
import com.demo.network.domain.model.ServiceResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LoginRepositoryImpl(
    private val dataSource: LoginDataSource
) : LoginRepository {

    override fun validateUser(
        username: String,
        password: String
    ): Flow<ServiceResult<User>> {
        return dataSource.validateUser(username, password).map {
            when(it){
                is ServiceResult.Success -> ServiceResult.Success(it.data.toDomain())
                is ServiceResult.Error -> ServiceResult.Error(it.serviceError)
                ServiceResult.Loading -> ServiceResult.Loading
            }
        }
    }
}