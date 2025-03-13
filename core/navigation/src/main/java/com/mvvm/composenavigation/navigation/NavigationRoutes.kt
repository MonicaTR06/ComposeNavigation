package com.mvvm.composenavigation.navigation

import com.demo.navigation.Route
import kotlinx.serialization.Serializable

@Serializable
data object ReminderListRoute : Route

@Serializable
data object ReminderScreenRoute : Route

@Serializable
data object LoginRoute : Route

//NOTES
@Serializable
data object CreateNoteRoute : Route

@Serializable
data object NoteListRoute : Route

//TASK
@Serializable
data object AddTaskRoute : Route

@Serializable
data object TaskListRoute : Route