package com.example.reminder.presentation.ui

sealed class ReminderUiAction {
    data class OnMessageChanged(val title: String) : ReminderUiAction()
    data class OnDateTimeChanged(val dateTime: String) : ReminderUiAction()
    data class OnIsReminderOpenChanged(val isReminderOpen: Boolean) : ReminderUiAction()
    object SaveReminder : ReminderUiAction()
}