package dev.yuanzix.cyclist.dash.presentation.bicycle_details

import dev.yuanzix.cyclist.core.domain.util.NetworkError

sealed interface BicycleDetailsEvent {
    data object CouldNotFetchBicycleData : BicycleDetailsEvent
    data object ShortDurationNotAllowed : BicycleDetailsEvent
    data object RentedSuccessfully : BicycleDetailsEvent

    data class Error(val error: NetworkError) : BicycleDetailsEvent
}