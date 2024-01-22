package android.compose.data.repository.auth

import android.compose.util.Resource
import android.compose.data.remote.request.LoginRequest
import android.compose.data.remote.request.RegisterRequest
import android.compose.data.remote.response.LoginResponse
import android.compose.data.remote.response.RegisterResponse
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun loginUser(loginRequest: LoginRequest): Flow<Resource<LoginResponse>>
    suspend fun registerUser(registerRequest: RegisterRequest): Flow<Resource<Any>>
    suspend fun logoutUser()
}