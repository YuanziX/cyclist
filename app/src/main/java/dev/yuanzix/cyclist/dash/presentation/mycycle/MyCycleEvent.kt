package dev.yuanzix.cyclist.dash.presentation.mycycle

import dev.yuanzix.cyclist.core.domain.util.NetworkError

sealed interface MyCycleEvent {
    data class Error(val error: NetworkError) : MyCycleEvent

}