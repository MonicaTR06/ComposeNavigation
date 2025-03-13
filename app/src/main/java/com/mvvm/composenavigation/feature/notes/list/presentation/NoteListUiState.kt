package com.mvvm.composenavigation.feature.notes.list.presentation

import com.mvvm.composenavigation.feature.notes.create.data.response.NoteResponse

data class NoteListUiState(
    val isLoading: Boolean = false,
    val displayErrorMessage: Boolean = false,
    val notes: List<NoteResponse> = arrayListOf()
)