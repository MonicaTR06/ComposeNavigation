package com.mvvm.composenavigation.networking.model

data class ErrorResponse(
    val message : String,
    val status : String,
    val code : String,
    val timestamp: String
)