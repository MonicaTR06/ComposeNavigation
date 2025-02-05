package com.demo.login.presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.demo.login.R
import com.demo.login.presentation.ui.LoginUiAction
import com.demo.login.presentation.ui.UserDataModel
import com.demo.ui.theme.ComposeNavigationTheme
import com.demo.ui.tooling.DevicePreviews

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(factory = LoginViewModel.Factory),
    onSuccessLogin: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    //Se utiliza para ejecutar efectos secundarios que no deben ser parte del ciclo de vida de recomposición normal
    LaunchedEffect(uiState.errorMessage, uiState.errorMessageRes) {
        val message =
            uiState.errorMessage ?: uiState.errorMessageRes?.let { context.getString(it) } ?: ""

        if (message.isNotEmpty()) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            viewModel.serviceErrorShown()
        }
    }
    LaunchedEffect(uiState.successLogin) {
        if (uiState.successLogin) {
            onSuccessLogin()
            viewModel.successfulStatusReduced()
        }
    }

    LoginScreen(
        isLoading = uiState.isLoading,
        username = uiState.userData.username,
        password = uiState.userData.password,
        onUiAction = viewModel::onUiAction
    )
}

@Composable
internal fun LoginScreen(
    isLoading: Boolean,
    username: String,
    password: String,
    onUiAction: (LoginUiAction) -> Unit
) {

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.login_title),
                style = MaterialTheme.typography.headlineLarge
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                value = username,
                onValueChange = {
                    onUiAction(LoginUiAction.OnUsernameChanged(it))
                },
                placeholder = {
                    Text(text = stringResource(R.string.user_hint))
                },
                singleLine = true,
                enabled = isLoading.not()
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                value = password,
                onValueChange = {
                    onUiAction(LoginUiAction.OnPasswordChanged(it))
                },
                placeholder = {
                    Text(text = stringResource(R.string.password_hint))
                },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                enabled = isLoading.not()
            )

            Button(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .height(48.dp)
                    .fillMaxWidth(),
                onClick = {
                    onUiAction(LoginUiAction.Submit)
                },
                enabled = isLoading.not()
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

                    Text(text = stringResource(R.string.login_button))

                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.fillMaxHeight(),
                            color = MaterialTheme.colorScheme.secondary,
                            trackColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@DevicePreviews
@Composable
fun LoginScreenPreview(
    @PreviewParameter(LoginScreenPreviewProvider::class) params: UserDataModel
) {
    ComposeNavigationTheme {
        LoginScreen(
            isLoading = false,
            username = params.username,
            password = params.password,
            onUiAction = {})
    }
}
