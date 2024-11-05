package dev.yuanzix.cyclist.dash.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Bicycles(
    val page: Int,
    @SerialName("total_pages") val totalPages: Int,
    val count: Int,
    val bikes: List<Bicycle>
)
