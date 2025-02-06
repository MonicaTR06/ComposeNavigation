package com.example.notes.presentation

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.notes.presentation.ui.NotesUiAction
import com.example.notes.presentation.ui.NotesUiState
import com.mvvm.composenavigation.R
import com.mvvm.composenavigation.feature.notes.create.data.datasource.NotesDataSource
import com.mvvm.composenavigation.feature.notes.create.data.request.CreateNoteRequest
import com.mvvm.composenavigation.networking.model.ServiceError
import com.mvvm.composenavigation.networking.model.ServiceResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update

class NotesViewModel (
    private val notesDataSource: NotesDataSource
) : ViewModel()  {
    private var _uiState = MutableStateFlow(NotesUiState())
    val uiState = _uiState.asStateFlow()

    fun saveNote() {
        createNote(_uiState.value.title, _uiState.value.description, _uiState.value.isFavorite)
    }

    private fun createNote(title: String, description: String, isFavorite: Boolean) {
        notesDataSource.createNotes(CreateNoteRequest(title, description,isFavorite)).onStart {
            emit(ServiceResult.Loading)
        }.map { createNoteResponse ->
            when (createNoteResponse) {
                is ServiceResult.Error -> {
                    when (createNoteResponse.serviceError) {
                        ServiceError.NetworkError -> {

                            println(R.string.network_message)
                            displayServiceErrorMessage(R.string.network_message)
                            println("serviceError")
                        }
                        is ServiceError.ServerError -> {
                            displayServiceErrorMessage(createNoteResponse.serviceError.errorData.message)
                            println(createNoteResponse.serviceError.errorData.message)
                            println("ServerError")
                        }
                        ServiceError.Unexpected -> {
                            displayServiceErrorMessage(R.string.unexpected_message)
                            println(R.string.unexpected_message)
                            println("Mensaje Inesperado")
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
                    println("Se ha guardado la nota saisfactoriamente!")
                    _uiState.update {
                        it.copy(
                            succesMessageRes = R.string.create_note_success_message
                        )
                    }
                }
            }

        }.launchIn(viewModelScope)
    }
    private fun displayServiceErrorMessage(@StringRes stringRes: Int) {
        _uiState.update {
            it.copy(
                errorMessageRes = stringRes
                ,isLoading = false
            )
        }
    }

    private fun displayServiceErrorMessage(stringRes: String) {
        _uiState.update {
            it.copy(
                errorMessage = stringRes
               , isLoading = false
            )
        }
    }

    fun successMessageShown() {
        _uiState.update {
            it.copy(
                succesMessageRes = null
            )
        }
    }

    fun errorMessageShown() {
        _uiState.update {
            it.copy(
                errorMessageRes = null
            )
        }
    }

    fun onTitleChanged(newTitle: String) {
        _uiState.update {
            it.copy(
                title = newTitle
            )
        }
    }

    fun onDescriptionChanged(newDescription: String) {
        _uiState.update {
            it.copy(
                description = newDescription
            )
        }
    }


    fun onFavoriteChanged() {
        val currentFavorite : Boolean = _uiState.value.isFavorite
        _uiState.update {
            it.copy(
                isFavorite = !currentFavorite
            )
        }
    }

    fun onUiAction(it: NotesUiAction) {
        when(it){
            is NotesUiAction.OnDescriptionChanged -> onDescriptionChanged(it.description)
            NotesUiAction.OnFavoriteChanged -> onFavoriteChanged()
            NotesUiAction.OnSaveNote -> saveNote()
            is NotesUiAction.OnTitleChanged -> onTitleChanged(it.title)
        }

    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                NotesViewModel(NotesDataSource())
            }
        }
    }
}
