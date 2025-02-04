package com.demo.network.data.mapper

import com.demo.network.data.model.ErrorResponse
import com.demo.network.domain.model.ErrorMessage

fun ErrorResponse.toDomain() = ErrorMessage(message, code)