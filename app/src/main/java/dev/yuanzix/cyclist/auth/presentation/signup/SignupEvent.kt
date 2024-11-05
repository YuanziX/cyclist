package dev.yuanzix.cyclist.auth.presentation.signup

import dev.yuanzix.cyclist.core.domain.util.NetworkError

sealed interface SignupEvent {
    data object NavigateToDash : SignupEvent
    data object NavigateToLogin : SignupEvent
    data class Error(val message: NetworkError) : SignupEvent
}