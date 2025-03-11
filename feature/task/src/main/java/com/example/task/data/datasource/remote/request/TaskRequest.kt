package com.example.task.data.datasource.remote.request

data class TaskRequest(
    val title: String = "",
    val isTaskOpen: Boolean = false,
    val priority: String = ""
)