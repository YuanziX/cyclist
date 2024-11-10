package dev.yuanzix.cyclist.core.presentation.start

import dev.yuanzix.cyclist.core.navigation.Destination

sealed interface StartEvent {
    data class ContinueNavigation(val destination: Destination) : StartEvent
}