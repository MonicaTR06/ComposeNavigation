package com.example.task.data.datasource.remote.response

data class CreateTaskResponse (
    val id : Int,
    val title : String,
    val isTaskOpen : Boolean,
    val priority : String
)