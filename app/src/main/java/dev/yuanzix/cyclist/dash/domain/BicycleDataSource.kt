package dev.yuanzix.cyclist.dash.domain

import dev.yuanzix.cyclist.core.domain.util.NetworkError
import dev.yuanzix.cyclist.core.domain.util.Result
import dev.yuanzix.cyclist.dash.data.RentedBikeDto
import dev.yuanzix.cyclist.dash.data.SuccessResponseDto

interface BicycleDataSource {
    suspend fun getBicycles(pageNo: Int): Result<Bicycles, NetworkError>
    suspend fun getBicycleById(bicycleId: String): Result<Bicycle, NetworkError>

    suspend fun getRentedBicycle(token: String): Result<RentedBikeDto, NetworkError>
    suspend fun rentBicycle(
        bikeId: String,
        startTime: String,
        endTime: String,
        token: String
    ): Result<SuccessResponseDto, NetworkError>
}