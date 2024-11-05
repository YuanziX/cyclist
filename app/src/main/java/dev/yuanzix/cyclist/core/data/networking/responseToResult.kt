package dev.yuanzix.cyclist.core.data.networking

import dev.yuanzix.cyclist.core.domain.util.NetworkError
import dev.yuanzix.cyclist.core.domain.util.Result
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse

suspend inline fun <reified T> responseToResult(response: HttpResponse): Result<T, NetworkError> {
    return when (response.status.value) {
        in 200..299 -> {
            try {
                Result.Success(response.body<T>())
            } catch (e: NoTransformationFoundException) {
                Result.Error(NetworkError.SerializationError)
            }
        }

        404 -> Result.Error(NetworkError.NotFound)
        408 -> Result.Error(NetworkError.RequestTimeout)
        429 -> Result.Error(NetworkError.TooManyRequests)
        in 500..599 -> Result.Error(NetworkError.ServerError)
        else -> {
            try {
                Result.Error(NetworkError.KnownError(response.body<NetworkError.KnownError>().error))
            } catch (e: Exception) {
                Result.Error(NetworkError.UnknownError)
            }
        }
    }
}