package com.mvvm.composenavigation.feature.notes.create.data.response

data class NoteResponse (
    val id : Int,
    val title : String,
    val isFavorite : Boolean
)