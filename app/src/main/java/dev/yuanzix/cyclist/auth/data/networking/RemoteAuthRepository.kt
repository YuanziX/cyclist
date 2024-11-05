package dev.yuanzix.cyclist.auth.data.networking

import dev.yuanzix.cyclist.auth.domain.AuthRepository
import dev.yuanzix.cyclist.core.data.networking.constructUrl
import dev.yuanzix.cyclist.core.data.networking.safeCall
import dev.yuanzix.cyclist.core.domain.util.NetworkError
import dev.yuanzix.cyclist.core.domain.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpHeaders

class RemoteAuthRepository(
    private val ktorClient: HttpClient
) : AuthRepository {
    override suspend fun verifyToken(token: String): Result<Verify, NetworkError> {
        return safeCall<Verify> {
            ktorClient.get(urlString = constructUrl("user/verify")) {
                header(HttpHeaders.Authorization, "Bearer $token")
            }
        }
    }

    override suspend fun login(
        email: String,
        password: String
    ): Result<AuthSuccess, NetworkError> {
        return safeCall<AuthSuccess> {
            ktorClient.post(urlString = constructUrl("user/login")) {
                setBody(mapOf("email" to email, "password" to password))
            }
        }
    }

    override suspend fun signup(
        name: String,
        email: String,
        password: String
    ): Result<AuthSuccess, NetworkError> {
        return safeCall<AuthSuccess> {
            ktorClient.post(urlString = constructUrl("user/signup")) {
                setBody(mapOf("name" to name, "email" to email, "password" to password))
            }
        }
    }
}