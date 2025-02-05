package com.mvvm.composenavigation.feature.task.add.presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mvvm.composenavigation.R
import com.mvvm.composenavigation.feature.task.add.presentation.TaskConstants.HIGHT
import com.mvvm.composenavigation.feature.task.add.presentation.TaskConstants.LOW
import com.mvvm.composenavigation.feature.task.presentation.TaskUiAction
import com.mvvm.composenavigation.feature.task.presentation.TaskViewModel

@Preview
@Composable
fun AddTaskScreen(
    viewModel: TaskViewModel = viewModel(factory = TaskViewModel.Factory)
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    if (uiState.errorMessage != null || uiState.errorMessageRes != null) {
        val message = uiState.errorMessage ?: uiState.errorMessageRes?.let { stringResource(it) }
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        viewModel.serviceErrorShown()
    }
    if(uiState.isSave){
        Toast.makeText(context, R.string.saved_task, Toast.LENGTH_SHORT).show()
        viewModel.taskMessageShow()
    }

    AddTaskScreen(
        isLoading = uiState.isLoading,
        title = uiState.title,
        priority = uiState.priority,
        isTaskOpen = uiState.isTaskOpen,
        onUiAction = viewModel::onUiAction,

    )
}

@Composable
internal fun AddTaskScreen(
    isLoading: Boolean,
    title: String,
    priority: String,
    isTaskOpen: Boolean,
    onUiAction: (TaskUiAction) -> Unit,
) {

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(MaterialTheme.colorScheme.background),

            verticalArrangement = Arrangement.Center
        ) {

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.title_work),
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Cursive,
                color = Color.Blue,
                style = MaterialTheme.typography.headlineLarge
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                value = title,
                onValueChange = {
                    onUiAction(TaskUiAction.OnTitleChanged(it))
                },
                placeholder = {
                    Text(text = stringResource(R.string.u_work))
                },
                singleLine = true,
                enabled = isLoading.not()
            )

            PriorityDropdownMenu(onUiAction,priority)
            SwitchMinimalExample(onUiAction,isTaskOpen)

            Button(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .height(48.dp)
                    .fillMaxWidth(),
                onClick = {
                    onUiAction(TaskUiAction.Save)

                },
                enabled = isLoading.not()
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

                    Text(text = stringResource(R.string.save))

                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            }

            Button(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .height(48.dp)
                    .fillMaxWidth(),
                onClick = {
                    onUiAction(TaskUiAction.Clean)

                },
                enabled = isLoading.not()
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

                    Text(text = stringResource(R.string.clean_screen_Task))
                }
            }

        }
    }

}

@Composable
fun SwitchMinimalExample(onUiAction: (TaskUiAction) -> Unit, checked: Boolean) {

    Row(
        modifier = Modifier.padding(top = 20.dp)
    ) {
        Text(text = stringResource(R.string.open_close))
        Switch(
            checked = checked,
            onCheckedChange = {
                onUiAction(TaskUiAction.OnIsTaskOpenChanged(it))
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PriorityDropdownMenu(onUiAction: (TaskUiAction) -> Unit, selectedText: String) {
    val priority = arrayOf(HIGHT,LOW)
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                value = selectedText,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor(),
                label = { Text("Prioridad")}
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                priority.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            expanded = false
                            onUiAction(TaskUiAction.OnPriorityChanged(item))
                        }
                    )
                }
            }
        }
    }
}