package com.example.task.presentation.create.ui

import androidx.annotation.StringRes

data class TaskUiState(
    val isLoading: Boolean = false,
    val title: String = "",
    val isTaskOpen: Boolean = false,
    val priority: String = "",
    val isSave: Boolean = false,
    @StringRes
    val errorMessageRes: Int? = null,
    val errorMessage: String? = null
)
data class UserDataModel(
    val title: String,
    val isTaskOpen: Boolean,
    val priority: String
)