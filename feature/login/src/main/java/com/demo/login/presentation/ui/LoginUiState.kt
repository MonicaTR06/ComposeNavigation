package com.demo.login.presentation.ui

import androidx.annotation.StringRes

data class LoginUiState(
    val isLoading: Boolean = false,
    val userData: UserDataModel = UserDataModel(),
    @StringRes
    val errorMessageRes: Int? = null,
    val errorMessage: String? = null,
    val successLogin: Boolean = false
)

data class UserDataModel(
    val username: String = "",
    val password: String = ""
)