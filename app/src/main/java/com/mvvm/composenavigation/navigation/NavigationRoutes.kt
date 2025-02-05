package com.mvvm.composenavigation.navigation

import kotlinx.serialization.Serializable

@Serializable
data object ReminderListRoute : Route

@Serializable
data object TaskListRoute : Route

@Serializable
data object ReminderScreenRoute : Route

@Serializable
data object AddTaskRoute : Route

//NOTES
@Serializable
data object CreateNoteRoute : Route

@Serializable
data object NoteListRoute : Route
