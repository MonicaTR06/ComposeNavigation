package com.example.task.data.datasource.mapper

import com.example.task.data.datasource.remote.response.SaveTaskResponse
import com.example.task.domain.model.Task

fun SaveTaskResponse.toDomain() = Task(
    title = title,
    isTaskOpen = isTaskOpen,
    priority = priority
)