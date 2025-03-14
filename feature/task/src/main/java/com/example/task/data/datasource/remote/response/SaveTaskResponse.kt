package com.example.task.data.datasource.remote.response

data class SaveTaskResponse(
    val title: String = "",
    val isTaskOpen: Boolean = false,
    val priority: String = ""
)