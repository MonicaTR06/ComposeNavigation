package com.example.notes.presentation.ui

sealed class NotesUiAction {
    data object OnFavoriteChanged : NotesUiAction()
    data class OnTitleChanged(val title: String): NotesUiAction()
    data class OnDescriptionChanged(val description: String): NotesUiAction()
    data object OnSaveNote : NotesUiAction()
}