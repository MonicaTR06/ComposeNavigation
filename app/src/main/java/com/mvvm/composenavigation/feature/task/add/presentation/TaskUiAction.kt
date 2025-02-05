package com.mvvm.composenavigation.feature.task.presentation

sealed class TaskUiAction {
    data class OnTitleChanged(val title: String): TaskUiAction()
    data class OnPriorityChanged(val priority: String): TaskUiAction()
    data class OnIsTaskOpenChanged(val isTaskOpen: Boolean): TaskUiAction()
    data object Save : TaskUiAction()
    data object Clean : TaskUiAction()
}