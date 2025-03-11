package com.example.task.data.datasource.mapper

import com.example.task.data.datasource.remote.response.TaskResponse
import com.example.task.domain.model.Task

fun TaskResponse.toDomain() = Task(
    title = title,
    isTaskOpen = isTaskOpen,
    priority = priority
)