package dev.yuanzix.cyclist.auth.presentation.login

data class LoginState(
    val isLoading: Boolean = false,
    val email: String = "",
    val password: String = "",
    val hidePassword: Boolean = true,
)
