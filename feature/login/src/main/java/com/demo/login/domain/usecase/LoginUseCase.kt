package com.demo.login.domain.usecase

import com.demo.login.domain.repository.LoginRepository

class LoginUseCase(
    private val repository: LoginRepository
) {
    operator fun invoke(username: String, password: String) =
        repository.validateUser(username, password)
}