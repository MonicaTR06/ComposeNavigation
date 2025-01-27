package com.mvvm.composenavigation.feature.task.add.data.request

data class TaskRequest(
    val title: String = "",
    val isTaskOpen: Boolean = false,
    val priority: String = ""
)