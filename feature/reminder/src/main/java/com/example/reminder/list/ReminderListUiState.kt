package com.example.reminder.list

import com.example.reminder.data.datasource.remote.response.ReminderResponse

data class ReminderListUiState (
    val isLoading: Boolean = false,
    val displayErrorMessage: Boolean = false,
    val reminders: List<ReminderResponse> = arrayListOf(),
)