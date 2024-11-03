package dev.yuanzix.cyclist.auth.presentation.login

sealed interface LoginAction {
    data object NavigateToSignup: LoginAction
    data object OnLoginClicked : LoginAction
    data object TogglePasswordVisibility : LoginAction
    data class OnEmailChanged(val email: String) : LoginAction
    data class OnPasswordChanged(val password: String) : LoginAction
}