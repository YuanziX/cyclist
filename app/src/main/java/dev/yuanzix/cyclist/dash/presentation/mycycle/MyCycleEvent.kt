package dev.yuanzix.cyclist.dash.presentation.mycycle

import dev.yuanzix.cyclist.core.domain.util.NetworkError

sealed interface MyCycleEvent {
    data object CycleUnlockedSuccessfully : MyCycleEvent
    data class ShowMessage(val message: String) : MyCycleEvent
    data class Error(val error: NetworkError) : MyCycleEvent
}