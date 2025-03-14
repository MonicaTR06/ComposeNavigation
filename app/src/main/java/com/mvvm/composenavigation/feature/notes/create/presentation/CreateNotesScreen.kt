package com.mvvm.composenavigation.feature.notes.create.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bcp.bank.bcp.mvvm.feature.notes.presentation.NotesUiAction
import com.mvvm.composenavigation.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNotesScreen(
    viewModel: NotesViewModel = viewModel(factory = NotesViewModel.Factory),
    onBack: () -> Unit
) {
    Scaffold(

        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.notes_title))
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.menu_helper_description)
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    ) { contentPadding ->
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        val context = LocalContext.current

        if (uiState.succesMessageRes != null){
            Toast.makeText(context, stringResource(uiState.succesMessageRes!!), Toast.LENGTH_LONG).show()
            viewModel.successMessageShown()
        }
        if(uiState.errorMessage != null || uiState.errorMessageRes != null){
            val message = uiState.errorMessage ?: uiState.errorMessageRes?.let { stringResource(it) }
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            viewModel.errorMessageShown()
        }

        Box(
            Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(16.dp)
        ) {
            Notes(
                modifier = Modifier.align(Alignment.Center),
                onUiAction = {
                    viewModel.onUiAction(it)
                },
                title = uiState.title,
                description = uiState.description,
                isFavorite = uiState.isFavorite
            )
        }
    }
}
@Composable
internal fun Notes(modifier: Modifier, onUiAction: (NotesUiAction) -> Unit, title: String, description: String, isFavorite: Boolean) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.notes_description),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        Spacer(modifier = Modifier.padding(16.dp))
        FavoriteButton(
            isFavorite = isFavorite,
            onClick = {onUiAction.invoke(NotesUiAction.OnFavoriteChanged)}
        )
        Spacer(modifier = Modifier.padding(8.dp))
        TitleField(
            value = title,
            onValueChange = { onUiAction.invoke(NotesUiAction.OnTitleChanged(it))}
        )
        Spacer(modifier = Modifier.padding(8.dp))
        DescriptionField(
            value = description,
            onValueChange = { onUiAction.invoke(NotesUiAction.OnDescriptionChanged(it))}
        )

        Spacer(modifier = Modifier.padding(8.dp))
        SendBtn(onClick = { onUiAction.invoke(NotesUiAction.OnSaveNote) })
    }
}

@Composable
fun TitleField(value: String, onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Título") },
        singleLine = true,
        maxLines = 1
    )
}

@Composable
fun DescriptionField(value: String, onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        placeholder = { Text(text = "Inserta Descripción") },
        maxLines = 5
    )
}

@Composable
fun FavoriteButton(isFavorite: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isFavorite) Color.Blue else Color.Gray
        )
    ) {
        Text(text = if (isFavorite) "Favorito" else "No Favorito")
    }
}

@Composable
fun SendBtn(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
    ) {
        Text(text = "Guardar Nota")
    }
}
/*
@Preview(showBackground = true, showSystemUi = true)
@Composable
internal fun NotesScreenPreview() {
    MVVMTheme {
        NotesScreen()
    }
}
*/