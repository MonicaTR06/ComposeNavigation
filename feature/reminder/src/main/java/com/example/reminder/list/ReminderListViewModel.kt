package com.example.reminder.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.demo.network.domain.model.ServiceResult
import com.example.reminder.data.datasource.remote.ReminderDataSource
import com.example.reminder.data.datasource.remote.response.RemindersResponse
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class ReminderListViewModel(
    private val reminderDataSource: ReminderDataSource,
): ViewModel() {

    val uiState: StateFlow<ReminderListUiState> = reminderDataSource.getReminders().onStart {
        emit(ServiceResult.Loading)
    }.map {
        mapResponse(it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ReminderListUiState(isLoading = true),
    )

    private fun mapResponse(response: ServiceResult<RemindersResponse>): ReminderListUiState {
        return when (response) {
            is ServiceResult.Error -> {
                ReminderListUiState(
                    isLoading = false,
                    displayErrorMessage = true
                )
            }

            ServiceResult.Loading -> {
                ReminderListUiState(
                    isLoading = true
                )
            }

            is ServiceResult.Success -> {
                ReminderListUiState(
                    isLoading = false,
                    reminders = response.data.list
                )
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                ReminderListViewModel(ReminderDataSource())
            }
        }
    }
}