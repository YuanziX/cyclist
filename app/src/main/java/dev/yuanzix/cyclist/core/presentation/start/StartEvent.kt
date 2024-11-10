package dev.yuanzix.cyclist.core.presentation.start

sealed interface StartEvent {
    object NavigateToLogin : StartEvent
    object NavigateToDash : StartEvent
}