package dev.yuanzix.cyclist.dash.presentation.mycycle

import dev.yuanzix.cyclist.dash.domain.Bicycle
import java.time.ZonedDateTime

data class MyCycleState(
    val isLoading: Boolean = true,
    val rentalId: String? = null,
    val bicycle: Bicycle? = null,
    val endTime: ZonedDateTime = ZonedDateTime.now(),
)