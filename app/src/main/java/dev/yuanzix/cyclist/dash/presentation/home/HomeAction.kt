package dev.yuanzix.cyclist.dash.presentation.home

sealed interface HomeAction {
    data class NavigateToCycleWithID(val id: String) : HomeAction
}