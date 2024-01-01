package android.compose.data.repository.auth

import android.compose.util.Resource
import android.compose.data.remote.request.LoginRequest
import android.compose.data.remote.request.RegisterRequest

interface AuthRepository {
    suspend fun loginUser(loginRequest: LoginRequest): Resource<Unit>

    suspend fun registerUser(registerRequest: RegisterRequest):Resource<Unit>
}