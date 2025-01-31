package com.mvvm.composenavigation.navigation

import kotlinx.serialization.Serializable

@Serializable
data object RememberListRoute : Route

@Serializable
data object NoteListRoute : Route

@Serializable
data object TaskListRoute : Route

@Serializable
data object CreateNoteRoute : Route