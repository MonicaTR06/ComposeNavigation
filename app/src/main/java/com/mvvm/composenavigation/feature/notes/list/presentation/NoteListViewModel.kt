package com.mvvm.composenavigation.feature.notes.list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.mvvm.composenavigation.feature.notes.create.data.datasource.NotesDataSource
import com.mvvm.composenavigation.feature.notes.create.data.response.NotesResponse
import com.mvvm.composenavigation.networking.model.ServiceResult
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class NoteListViewModel(
    private val notesDataSource: NotesDataSource,
) : ViewModel() {

    val uiState: StateFlow<NoteListUiState> =
        notesDataSource.getNotes().onStart {
            emit(ServiceResult.Loading)
        }.map {
            mapResponse(it)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = NoteListUiState(isLoading = true),
        )

    private fun mapResponse(response: ServiceResult<NotesResponse>): NoteListUiState {
        return when (response) {
            is ServiceResult.Error -> {
                NoteListUiState(
                    isLoading = false,
                    displayErrorMessage = true
                )
            }

            ServiceResult.Loading -> {
                NoteListUiState(
                    isLoading = true
                )
            }

            is ServiceResult.Success -> {
                NoteListUiState(
                    isLoading = false,
                    notes = response.data.list
                )
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                NoteListViewModel(NotesDataSource())
            }
        }
    }
}