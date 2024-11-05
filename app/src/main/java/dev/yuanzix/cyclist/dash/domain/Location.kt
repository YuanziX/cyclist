package dev.yuanzix.cyclist.dash.domain

import kotlinx.serialization.Serializable

@Serializable
data class Location(
    var lat: Double,
    var lng: Double,
)
