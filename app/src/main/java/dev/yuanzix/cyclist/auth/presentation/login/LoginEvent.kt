package dev.yuanzix.cyclist.auth.presentation.login

import dev.yuanzix.cyclist.core.domain.util.NetworkError

sealed interface LoginEvent {
    data object NavigateToDash : LoginEvent
    data object NavigateToSignup : LoginEvent
    data class Error(val message: NetworkError) : LoginEvent
}