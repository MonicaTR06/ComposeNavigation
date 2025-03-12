package com.mvvm.composenavigation.feature.notes.list.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mvvm.composenavigation.R
import com.mvvm.composenavigation.feature.notes.create.data.response.NoteResponse

@Composable
fun NotesListScreen(
    viewModel: NoteListViewModel = viewModel(factory = NoteListViewModel.Factory),
    openDrawer: () -> Unit,
    onAddNotes: () -> Unit,
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    NotesListScreen(
        uiState.notes,
        openDrawer,
        onAddNotes
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun NotesListScreen(
    notes: List<NoteResponse>,
    openDrawer: () -> Unit,
    onAddNotes: () -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.notes_title))
                },
                navigationIcon = {
                    IconButton(onClick = openDrawer) {
                        Icon(
                            Icons.Filled.Menu,
                            contentDescription = stringResource(R.string.menu_helper_description)
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddNotes
            ) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = stringResource(R.string.create_notes_helper_description)
                )
            }
        }
    ) { contentPadding ->
        Column(modifier = Modifier.padding(contentPadding)) {
            Text(text = stringResource(R.string.notes_list_description))

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                items(notes) { note ->
                    Text(text = note.title)
                }
            }
        }

    }
}