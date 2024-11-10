package dev.yuanzix.cyclist.dash.data

import dev.yuanzix.cyclist.dash.domain.Bicycle
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RentedBikeDto(
    @SerialName("_id") val rentalId: String,
    val bike: Bicycle,
    val endTime: String,
)

@Serializable
data class SuccessResponseDto(
    val message: String,
)