package dev.yuanzix.cyclist.dash.presentation.home

import dev.yuanzix.cyclist.core.domain.util.NetworkError

sealed interface HomeEvent {
    data class NavigateToCycleWithID(val id: String) : HomeEvent
    data class Error(val error: NetworkError) : HomeEvent
}