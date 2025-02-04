package com.demo.login.data.datasource.remote.service

import com.demo.login.data.datasource.remote.response.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LoginService {

    @GET(value = ApiURL.VALIDATE_USER)
    suspend fun validateUser(
        @Query("username") username: String,
        @Query("password") password: String,
    ): Response<UserResponse>

}