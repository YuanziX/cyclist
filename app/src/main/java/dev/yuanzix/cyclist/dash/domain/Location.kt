package dev.yuanzix.cyclist.dash.domain

import kotlinx.serialization.Serializable

@Serializable
data class Location(
    var lat: String,
    var lng: String,
)
