package com.mvvm.composenavigation.feature.task.presentation

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.mvvm.composenavigation.R
import com.mvvm.composenavigation.feature.task.add.data.request.TaskDataSource
import com.mvvm.composenavigation.feature.task.add.data.request.TaskRequest
import com.mvvm.composenavigation.networking.model.ServiceError
import com.mvvm.composenavigation.networking.model.ServiceResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update

class TaskViewModel(
    private val taskDataSource: TaskDataSource
) : ViewModel() {
    private var _uiState = MutableStateFlow(TaskUiState())
    val uiState = _uiState.asStateFlow()

    fun onUiAction(uiAction: TaskUiAction) {
        when (uiAction) {
            is TaskUiAction.OnTitleChanged -> {
                updateTitle(uiAction.title)
            }

            is TaskUiAction.OnIsTaskOpenChanged -> {
                updateIsTaskOpen(uiAction.isTaskOpen)
            }

            is TaskUiAction.OnPriorityChanged -> {
                updatePriority(uiAction.priority)
            }

            is TaskUiAction.Save -> saveTask(_uiState.value)
            TaskUiAction.Clean -> cleanTask()
        }
    }

    private fun saveTask(value: TaskUiState) {
        val taskRequest = TaskRequest(
            title = value.title,
            isTaskOpen = value.isTaskOpen,
            priority = value.priority
        )
        taskDataSource.createTask(taskRequest).onStart {
            emit(ServiceResult.Loading)

        }.map {
            when (it) {
                is ServiceResult.Error -> {
                    when (it.serviceError) {
                        ServiceError.NetworkError -> {
                            displayServiceErrorMessage(R.string.network_message)
                        }

                        is ServiceError.ServerError -> {
                            displayServiceErrorMessage(it.serviceError.errorData.message)
                        }

                        ServiceError.Unexpected -> {
                            displayServiceErrorMessage(R.string.unexpected_message)
                        }
                    }
                }

                ServiceResult.Loading -> {
                    _uiState.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                }

                is ServiceResult.Success -> {
                    _uiState.update {
                        it.copy(
                            isSave = true,
                            title = "",
                            priority = "",
                            isTaskOpen = false,
                            isLoading = false
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun cleanTask() {
        _uiState.update {
            TaskUiState()
        }
    }


    fun serviceErrorShown() {
        _uiState.update {
            it.copy(
                errorMessageRes = null,
                errorMessage = null
            )
        }
    }

    private fun displayServiceErrorMessage(@StringRes stringRes: Int) {
        _uiState.update {
            it.copy(
                errorMessageRes = stringRes,
                isLoading = false
            )
        }
    }

    private fun displayServiceErrorMessage(stringRes: String) {
        _uiState.update {
            it.copy(
                errorMessage = stringRes,
                isLoading = false
            )
        }
    }

    private fun updateTitle(newTitle: String) {
        _uiState.update {
            it.copy(title = newTitle)
        }
    }

    private fun updateIsTaskOpen(newIsTaskOpen: Boolean) {
        _uiState.update {
            it.copy(isTaskOpen = newIsTaskOpen)
        }
    }

    private fun updatePriority(newPriority: String) {
        _uiState.update {
            it.copy(priority = newPriority)
        }
    }

    fun taskMessageShow() {
        _uiState.update {
            it.copy(isSave = false)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                TaskViewModel(TaskDataSource())
            }
        }
    }
}