package com.demo.login.presentation

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.demo.login.R
import com.demo.login.data.datasource.remote.LoginDataSource
import com.demo.login.data.repository.LoginRepositoryImpl
import com.demo.login.domain.usecase.LoginUseCase
import com.demo.login.presentation.ui.LoginUiAction
import com.demo.login.presentation.ui.LoginUiState
import com.demo.login.presentation.ui.UserDataModel
import com.demo.network.domain.model.ServiceError
import com.demo.network.domain.model.ServiceResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    private var _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    fun onUiAction(uiAction: LoginUiAction) {
        when (uiAction) {
            is LoginUiAction.OnPasswordChanged -> {
                updatePassword(uiAction.password)
            }

            is LoginUiAction.OnUsernameChanged -> {
                updateUsername(uiAction.username)
            }

            is LoginUiAction.Submit -> {
                validateUser(_uiState.value.userData)
            }
        }
    }

    private fun validateUser(user: UserDataModel) {
        loginUseCase(user.username, user.password).onStart {
            emit(ServiceResult.Loading)
        }.map { userResponse ->
            when (userResponse) {
                is ServiceResult.Error -> {
                    when (val error = userResponse.serviceError) {
                        ServiceError.NetworkError -> {
                            displayServiceErrorMessage(R.string.network_message)
                        }

                        is ServiceError.ServerError -> {
                            displayServiceErrorMessage(error.errorMessage.message)
                        }

                        ServiceError.Unexpected -> {
                            displayServiceErrorMessage(R.string.unexpected_message)
                        }
                    }
                }

                ServiceResult.Loading -> {
                    _uiState.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                }

                is ServiceResult.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            successLogin = true
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun serviceErrorShown() {
        _uiState.update {
            it.copy(
                errorMessageRes = null,
                errorMessage = null
            )
        }
    }

    private fun displayServiceErrorMessage(@StringRes stringRes: Int) {
        _uiState.update {
            it.copy(
                errorMessageRes = stringRes,
                isLoading = false
            )
        }
    }

    private fun displayServiceErrorMessage(stringRes: String) {
        _uiState.update {
            it.copy(
                errorMessage = stringRes,
                isLoading = false
            )
        }
    }

    private fun updateUsername(newUsername: String) {
        val newUser = _uiState.value.userData.copy(
            username = newUsername
        )
        _uiState.update {
            it.copy(userData = newUser)
        }
    }

    private fun updatePassword(newPassword: String) {
        val newUser = _uiState.value.userData.copy(
            password = newPassword
        )
        _uiState.update {
            it.copy(userData = newUser)
        }
    }

    fun successfulStatusReduced() {
        _uiState.update {
            it.copy(successLogin = false)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                LoginViewModel(LoginUseCase(LoginRepositoryImpl(LoginDataSource())))
            }
        }
    }
}