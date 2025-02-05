package com.demo.login.data.datasource.remote.response

data class UserResponse(
    val id: Int,
    val username: String,
    val email: String? = null,
    val photo: String? = null,
    val cellphone: String? = null
)