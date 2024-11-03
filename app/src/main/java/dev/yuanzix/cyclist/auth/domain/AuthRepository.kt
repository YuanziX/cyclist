package dev.yuanzix.cyclist.auth.domain

import dev.yuanzix.cyclist.auth.data.networking.AuthSuccess
import dev.yuanzix.cyclist.auth.data.networking.Verify
import dev.yuanzix.cyclist.core.domain.util.NetworkError
import dev.yuanzix.cyclist.core.domain.util.Result

interface AuthRepository {
    suspend fun verifyToken(token: String): Result<Verify, NetworkError>
    suspend fun login(email: String, password: String): Result<AuthSuccess, NetworkError>
    suspend fun signup(name: String, email: String, password: String): Result<AuthSuccess, NetworkError>
}