package com.example.reminder.data.datasource.remote.request

data class ReminderRequest (
    val title: String,
    val dateTime: String,
    val isReminderOpen: Boolean
)