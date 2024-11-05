package dev.yuanzix.cyclist.dash.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Bicycle(
    val location: Location,
    @SerialName("_id") val id: String,
    val model: String,
    val isAvailable: Boolean,
    val pricePerHour: Double,
    val pricePerDay: Double,
    val image: String,
)

@Serializable
data class BicycleByIdDto(
    val bike: Bicycle
)