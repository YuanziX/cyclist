package dev.yuanzix.cyclist.core.presentation.start

interface StartEvent {
    object NavigateToLogin: StartEvent
    object NavigateToHome: StartEvent
}