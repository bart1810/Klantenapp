package android.compose.data.repository.auth

import android.compose.util.Resource
import android.compose.data.remote.request.LoginRequest
import android.compose.data.remote.request.RegisterRequest
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun loginUser(loginRequest: LoginRequest): Resource<Unit>
    suspend fun authenticateUser(token: String): Flow<Resource<String>>

    suspend fun registerUser(registerRequest: RegisterRequest):Resource<Unit>
}