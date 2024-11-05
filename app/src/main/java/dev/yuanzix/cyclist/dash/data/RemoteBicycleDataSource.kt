package dev.yuanzix.cyclist.dash.data

import dev.yuanzix.cyclist.core.data.networking.constructUrl
import dev.yuanzix.cyclist.core.data.networking.safeCall
import dev.yuanzix.cyclist.core.domain.util.NetworkError
import dev.yuanzix.cyclist.core.domain.util.Result
import dev.yuanzix.cyclist.core.domain.util.map
import dev.yuanzix.cyclist.dash.domain.Bicycle
import dev.yuanzix.cyclist.dash.domain.BicycleByIdDto
import dev.yuanzix.cyclist.dash.domain.BicycleDataSource
import dev.yuanzix.cyclist.dash.domain.Bicycles
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpHeaders

class RemoteBicycleDataSource(
    private val ktorClient: HttpClient
) : BicycleDataSource {
    override suspend fun getBicycles(pageNo: Int): Result<Bicycles, NetworkError> {
        return safeCall<Bicycles> {
            ktorClient.get(urlString = constructUrl("bike")) {
                url {
                    parameters.append("page", pageNo.toString())
                }
            }
        }
    }

    override suspend fun getBicycleById(bicycleId: String): Result<Bicycle, NetworkError> {
        return safeCall<BicycleByIdDto> {
            ktorClient.get(urlString = constructUrl("bike/one")) {
                url {
                    parameters.append("id", bicycleId)
                }
            }
        }.map { it.bike }
    }

    override suspend fun getRentedBicycle(token: String): Result<RentedBikeDto, NetworkError> {
        return safeCall<RentedBikeDto> {
            ktorClient.get(urlString = constructUrl("rental/myBike")) {
                header(HttpHeaders.Authorization, "Bearer $token")
            }
        }
    }

    override suspend fun rentBicycle(
        bikeId: String,
        startTime: String,
        endTime: String,
        token: String
    ): Result<SuccessResponseDto, NetworkError> {
        return safeCall<SuccessResponseDto> {
            ktorClient.post(urlString = constructUrl("rental/rent")) {
                setBody(mapOf("bikeId" to bikeId, "startTime" to startTime, "endTime" to endTime))
                header(HttpHeaders.Authorization, "Bearer $token")
            }
        }
    }
}