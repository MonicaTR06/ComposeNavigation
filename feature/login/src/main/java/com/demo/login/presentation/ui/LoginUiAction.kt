package com.demo.login.presentation.ui

sealed class LoginUiAction {
    data class OnUsernameChanged(val username: String): LoginUiAction()
    data class OnPasswordChanged(val password: String): LoginUiAction()
    data object Submit : LoginUiAction()
}