package com.mvvm.composenavigation.feature.reminder.presentation

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.mvvm.composenavigation.R
import com.mvvm.composenavigation.feature.reminder.data.datasource.ReminderDataSource
import com.mvvm.composenavigation.feature.reminder.data.request.ReminderRequest
import com.mvvm.composenavigation.networking.model.ServiceError
import com.mvvm.composenavigation.networking.model.ServiceResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update

class ReminderViewModel (
    private val reminderDataSource: ReminderDataSource
) : ViewModel() {
    private var _uiState = MutableStateFlow(ReminderUiState())
    val uiState = _uiState.asStateFlow()

    fun onUiAction(uiAction: ReminderUiAction) {
        when (uiAction) {
            is ReminderUiAction.OnMessageChanged -> {
                updateTitle(uiAction.title)
            }
            is ReminderUiAction.OnDateTimeChanged -> {
                updateDateTime(uiAction.dateTime)
            }
            is ReminderUiAction.OnIsReminderOpenChanged -> {
                updateIsReminderOpen(uiAction.isReminderOpen)
            }
        }
    }

    fun saveReminder() {
        val currentState = _uiState.value
        val reminderRequest = ReminderRequest(
            title = currentState.title,
            dateTime = /*LocalDateTime.parse(currentState.dateTime),*/ currentState.dateTime,
            isReminderOpen = currentState.isReminderOpen
        )

        reminderDataSource.createReminder(reminderRequest).onStart {
            emit(ServiceResult.Loading)
        }.map { result ->
            when (result) {
                is ServiceResult.Loading -> {
                    _uiState.update { it.copy(isLoading = true) }
                }
                is ServiceResult.Success -> {
                    _uiState.update { it.copy(isLoading = false) }
                }
                is ServiceResult.Error -> {
                    when (result.serviceError) {
                        ServiceError.NetworkError -> {
                            displayServiceErrorMessage(R.string.network_message)
                        }
                        is ServiceError.ServerError -> {
                            displayServiceErrorMessage(result.serviceError.errorData.message)
                        }
                        ServiceError.Unexpected -> {
                            displayServiceErrorMessage(R.string.unexpected_message)
                        }
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun displayServiceErrorMessage(@StringRes stringRes: Int) {
        _uiState.update {
            it.copy(
                errorMessageRes = stringRes,
                isLoading = false
            )
        }
    }

    private fun displayServiceErrorMessage(message: String) {
        _uiState.update {
            it.copy(
                errorMessage = message,
                isLoading = false
            )
        }
    }

    private fun updateTitle(title: String) {
        _uiState.update { it.copy(title = title) }
    }

    private fun updateDateTime(dateTime: String) {
        _uiState.update { it.copy(dateTime = dateTime) }
    }

    private fun updateIsReminderOpen(isReminderOpen: Boolean) {
        _uiState.update { it.copy(isReminderOpen = isReminderOpen) }
    }

    fun serviceErrorShown() {
        _uiState.update {
            it.copy(
                errorMessageRes = null,
                errorMessage = null
            )
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                ReminderViewModel(ReminderDataSource())
            }
        }
    }
}