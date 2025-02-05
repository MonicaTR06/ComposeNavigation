package com.mvvm.composenavigation.feature.notes.create.data.response

import com.google.gson.annotations.SerializedName

data class CreateNoteResponse(
        val id : Int,
        val title : String,
        val description : String,
        val isFavorite : Boolean,
        val createdOn : String
)
