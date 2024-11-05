package dev.yuanzix.cyclist.dash.presentation.bicycle_details

import dev.yuanzix.cyclist.dash.domain.Bicycle
import java.time.LocalDateTime

data class BicycleDetailsState(
    val date: String = "",
    val isLoading: Boolean = true,
    val bicycle: Bicycle? = null,
    val startTime: LocalDateTime = LocalDateTime.now(),
    val endTime: LocalDateTime = LocalDateTime.now(),
)
