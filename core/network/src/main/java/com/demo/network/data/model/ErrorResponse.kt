package com.demo.network.data.model

data class ErrorResponse(
    val message : String,
    val status : String,
    val code : String,
    val timestamp: String
)