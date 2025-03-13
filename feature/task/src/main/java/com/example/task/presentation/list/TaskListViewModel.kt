package com.example.task.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.demo.network.domain.model.ServiceResult
import com.example.task.data.datasource.remote.response.TasksResponse
import com.example.task.presentation.list.ui.TaskListUiState
import com.mvvm.composenavigation.feature.task.add.data.request.TaskDataSource
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class TaskListViewModel(
    private val tasksDataSource: TaskDataSource,
) : ViewModel() {

    val uiState: StateFlow<TaskListUiState> =
        tasksDataSource.getTasks().onStart {
            emit(ServiceResult.Loading)
        }.map {
            mapResponse(it)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = TaskListUiState(isLoading = true),
        )

    private fun mapResponse(response: ServiceResult<TasksResponse>): TaskListUiState {
        return when (response) {
            is ServiceResult.Error -> {
                TaskListUiState(
                    isLoading = false,
                )
            }

            ServiceResult.Loading -> {
                TaskListUiState(
                    isLoading = true
                )
            }

            is ServiceResult.Success -> {
                TaskListUiState(
                    isLoading = false,
                    tasks = response.data.list
                )
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                TaskListViewModel(TaskDataSource())
            }
        }
    }
}