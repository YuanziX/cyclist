package dev.yuanzix.cyclist.core.domain.util

import kotlinx.serialization.Serializable

interface NetworkError : Error {
    object RequestTimeout : NetworkError
    object TooManyRequests : NetworkError
    object NoInternet : NetworkError
    object ServerError : NetworkError
    object SerializationError : NetworkError
    object UnknownError : NetworkError
    object NotFound : NetworkError

    @Serializable
    data class KnownError(val error: String) : NetworkError
}