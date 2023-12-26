package android.compose.data.auth

import android.compose.Resource
import android.compose.models.LoginRequest
import android.compose.models.LoginResponse
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun loginUser(loginRequest: LoginRequest): Flow<Resource<LoginResponse>>
}