package com.mvvm.composenavigation.feature.notes.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.mvvm.composenavigation.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesListScreen(
    //AQUI DEBE SER BACK PARA EL CREATENOTE EN VEZ DEL OPEN DRAWER
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
                            Icons.Filled.Menu,//AQUI IRIA BOTON DE BACK
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
        //AQUI VA EL CONTENIDO DE LA PAGINA
        Box(modifier = Modifier.padding(contentPadding)) {
            Text(text = "Listado de Notas")
        }
    }
}