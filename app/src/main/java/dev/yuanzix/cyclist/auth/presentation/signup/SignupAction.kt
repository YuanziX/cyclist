package dev.yuanzix.cyclist.auth.presentation.signup

sealed interface SignupAction {
    data object NavigateToLogin : SignupAction
    data object OnSignupClicked : SignupAction
    data object TogglePasswordVisibility : SignupAction
    data class OnNameChanged(val name: String) : SignupAction
    data class OnEmailChanged(val email: String) : SignupAction
    data class OnPasswordChanged(val password: String) : SignupAction
}