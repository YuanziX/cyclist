package dev.yuanzix.cyclist.auth.presentation.signup

data class SignupState(
    val isLoading: Boolean = false,
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val hidePassword: Boolean = true,
)
