package dev.yuanzix.cyclist.dash.presentation.home

import dev.yuanzix.cyclist.dash.domain.Bicycle

data class HomeState(
    val isLoading: Boolean = true,
    val bicycles: List<Bicycle> = emptyList(),
    val currentPage: Int = 0,
    val isLastPage: Boolean = false,
)
