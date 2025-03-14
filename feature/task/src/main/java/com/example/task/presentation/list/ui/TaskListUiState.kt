package com.example.task.presentation.list.ui

import com.example.task.data.datasource.remote.response.TaskItemResponse

class TaskListUiState (
    val isLoading: Boolean = false,
    val title: String = "",
    val isTaskOpen: Boolean = false,
    val priority: String = "",
    val tasks: List<TaskItemResponse> = arrayListOf()
)