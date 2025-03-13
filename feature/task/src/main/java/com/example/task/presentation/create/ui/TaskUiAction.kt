package com.example.task.presentation.create.ui

sealed class TaskUiAction {
    data class OnTitleChanged(val title: String): TaskUiAction()
    data class OnPriorityChanged(val priority: String): TaskUiAction()
    data class OnIsTaskOpenChanged(val isTaskOpen: Boolean): TaskUiAction()
    data object Save : TaskUiAction()
    data object Clean : TaskUiAction()
}