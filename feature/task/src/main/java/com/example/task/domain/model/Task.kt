package com.example.task.domain.model

data class Task(
    val title: String = "",
    val isTaskOpen: Boolean = false,
    val priority: String = ""
)