package com.demo.login.domain.repository

import com.demo.login.domain.model.User
import com.demo.network.domain.model.ServiceResult
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    fun validateUser(username: String, password: String): Flow<ServiceResult<User>>
}