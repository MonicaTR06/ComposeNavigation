package com.bcp.bank.bcp.mvvm.feature.notes.presentation

import androidx.annotation.StringRes

data class NotesUiState(
    val isFavorite : Boolean = false,
    val title : String = "",
    val description : String = "",
    val isLoading : Boolean = false,

    @StringRes
    val errorMessageRes: Int? = null,
    val errorMessage: String? = null,
    @StringRes
    val succesMessageRes: Int?  = null

)
