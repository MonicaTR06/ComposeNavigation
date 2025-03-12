package com.example.reminder.presentation

import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.reminder.R
import com.example.reminder.presentation.ui.ReminderUiAction

@Composable
fun ReminderScreen(
    viewModel: ReminderViewModel = viewModel(factory = ReminderViewModel.Factory),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(uiState.errorMessage, uiState.errorMessageRes) {
        val message =
            uiState.errorMessage ?: uiState.errorMessageRes?.let { context.getString(it) } ?: ""

        if (message.isNotEmpty()) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            viewModel.serviceErrorShown()
        }
    }

    ReminderScreen(
        title = uiState.title,
        dateTime = uiState.dateTime,
        isReminderOpen = uiState.isReminderOpen,
        onUiAction = viewModel::onUiAction
    )
}

@Composable
internal fun ReminderScreen(
    title: String,
    dateTime: String,
    isReminderOpen: Boolean,
    onUiAction: (ReminderUiAction) -> Unit,
) {
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
                value = title,
                onValueChange = { onUiAction(ReminderUiAction.OnMessageChanged(it)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(id = R.string.date_and_time),
                style = MaterialTheme.typography.labelLarge
            )
            TextField(
                value = dateTime,
                onValueChange = { onUiAction(ReminderUiAction.OnDateTimeChanged(it)) },
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
                    checked = isReminderOpen,
                    onCheckedChange = { onUiAction(ReminderUiAction.OnIsReminderOpenChanged(it))},
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = MaterialTheme.colorScheme.primary,
                        uncheckedThumbColor = MaterialTheme.colorScheme.onSurface
                    )
                )
                Text(
                    text = if (isReminderOpen) "Enabled" else "Disabled",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    onUiAction(ReminderUiAction.SaveReminder)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "SAVE")
            }
        }
    }
}