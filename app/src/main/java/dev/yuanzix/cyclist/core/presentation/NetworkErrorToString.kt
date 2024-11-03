package dev.yuanzix.cyclist.core.presentation

import android.content.Context
import dev.yuanzix.cyclist.R
import dev.yuanzix.cyclist.core.domain.util.NetworkError

fun NetworkError.toString(ctx: Context): String {
    return when (this) {
        is NetworkError.KnownError -> this.error
        NetworkError.RequestTimeout -> ctx.getString(R.string.error_request_timeout)
        NetworkError.TooManyRequests -> ctx.getString(R.string.error_too_many_requests)
        NetworkError.NoInternet -> ctx.getString(R.string.error_no_internet)
        NetworkError.SerializationError -> ctx.getString(R.string.error_serialization)
        else -> {
            ctx.getString(R.string.error_unknown)
        }
    }
}