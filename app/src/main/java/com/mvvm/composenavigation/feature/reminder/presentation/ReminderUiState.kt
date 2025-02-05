package com.mvvm.composenavigation.feature.reminder.presentation

data class ReminderUiState (
    val isLoading: Boolean = false,
    val title: String = "",
    val dateTime: String = "",
    val isReminderOpen: Boolean = false,
    val errorMessageRes: Int? = null,
    val errorMessage: String? = null
)