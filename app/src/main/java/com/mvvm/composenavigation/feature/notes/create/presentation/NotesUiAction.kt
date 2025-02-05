package com.bcp.bank.bcp.mvvm.feature.notes.presentation

sealed class NotesUiAction {
    data object OnFavoriteChanged : NotesUiAction()
    data class OnTitleChanged(val title: String): NotesUiAction()
    data class OnDescriptionChanged(val description: String): NotesUiAction()
    data object OnSaveNote : NotesUiAction()
}