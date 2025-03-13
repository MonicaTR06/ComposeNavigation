package com.example.reminder.data.datasource.remote.response

data class ReminderResponse (
    val id: Int,
    val title: String,
    val dueDate: String,
    val isReminderOpen: Boolean,
    val createdOn: String
)