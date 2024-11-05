package dev.yuanzix.cyclist.dash.presentation.bicycle_details

import java.time.LocalDateTime

sealed interface BicycleDetailsAction {
    data class SetStartTime(val time: LocalDateTime) : BicycleDetailsAction
    data class SetEndTime(val time: LocalDateTime) : BicycleDetailsAction
    data object RentBike : BicycleDetailsAction
}