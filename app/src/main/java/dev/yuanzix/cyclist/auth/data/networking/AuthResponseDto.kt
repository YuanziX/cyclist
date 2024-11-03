package dev.yuanzix.cyclist.auth.data.networking

import kotlinx.serialization.Serializable

@Serializable
data class Verify(
    val status: String
)

@Serializable
data class AuthSuccess(
    val token: String
)

@Serializable
data class AuthFailure(
    val error: String
)