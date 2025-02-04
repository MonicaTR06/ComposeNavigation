package com.demo.login.data.datasource.mapper

import com.demo.login.data.datasource.remote.response.UserResponse
import com.demo.login.domain.model.User

fun UserResponse.toDomain() = User(
    id = id,
    username = username,
    email = email,
    photo = photo,
    cellphone = cellphone
)