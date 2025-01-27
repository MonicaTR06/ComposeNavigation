package com.mvvm.composenavigation.feature.task.add.data.response

data class TaskResponse(
    val id: Int,
    val work: String,
    val date: String,
    val priority: String
)