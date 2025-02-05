package com.mvvm.composenavigation.feature.reminder.data.response

data class ReminderResponse (
    val id: Int? = null,
    val title: String? = null,
    val dateTime: String? = null,
    val isReminderOpen: Boolean? = null
)