package com.mvvm.composenavigation.feature.reminder.data.request

data class ReminderRequest (
    val title: String,
    val dateTime: String,
    val isReminderOpen: Boolean
)