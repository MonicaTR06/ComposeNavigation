package com.example.task.data.datasource.remote.mapper

import com.example.task.data.datasource.remote.request.TaskRequest
import com.example.task.domain.model.Task

fun Task.toData():TaskRequest{
    return TaskRequest(this.title,this.isTaskOpen,this.priority)
}
