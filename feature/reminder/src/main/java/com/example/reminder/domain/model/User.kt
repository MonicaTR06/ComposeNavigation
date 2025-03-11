package com.example.reminder.domain.model

data class User(
    val id: Int,
    val username: String,
    val email: String? = null,
    val photo: String? = null,
    val cellphone: String? = null
)