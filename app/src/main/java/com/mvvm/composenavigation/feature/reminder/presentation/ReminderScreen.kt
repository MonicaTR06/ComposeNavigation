package com.mvvm.composenavigation.feature.reminder.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mvvm.composenavigation.R

@Composable
fun ReminderScreen(
    viewModel: ReminderViewModel = viewModel(factory = ReminderViewModel.Factory),
    openDrawer: () -> Unit,
    onAddTask: () -> Unit

){
    val uiState = viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ){
            Text(
                text = stringResource(id = R.string.message),
                style = MaterialTheme.typography.labelLarge
            )
            TextField(
                value = uiState.value.title,
                onValueChange = { viewModel.onUiAction(ReminderUiAction.OnMessageChanged(it)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(id = R.string.date_and_time),
                style = MaterialTheme.typography.labelLarge
            )
            TextField(
                value = uiState.value.dateTime,
                onValueChange = { viewModel.onUiAction(ReminderUiAction.OnDateTimeChanged(it)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(id = R.string.reminder_open),
                style = MaterialTheme.typography.labelLarge
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Switch(
                    checked = uiState.value.isReminderOpen,
                    onCheckedChange = { viewModel.onUiAction(ReminderUiAction.OnIsReminderOpenChanged(it))},
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = MaterialTheme.colorScheme.primary,
                        uncheckedThumbColor = MaterialTheme.colorScheme.onSurface
                    )
                )
                Text(
                    text = if (uiState.value.isReminderOpen) "Enabled" else "Disabled",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    viewModel.saveReminder()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "SAVE")
            }

            if (uiState.value.errorMessageRes != null) {
                Text(
                    text = stringResource(id = uiState.value.errorMessageRes!!),
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 16.dp)
                )
            } else if (uiState.value.errorMessage != null) {
                Text(
                    text = uiState.value.errorMessage!!,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}
