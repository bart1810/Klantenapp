package android.compose.data.repository.auth

import android.compose.data.remote.request.ChangePasswordRequest
import android.compose.data.remote.request.ForgotPasswordRequest
import android.compose.util.Resource
import android.compose.data.remote.request.LoginRequest
import android.compose.data.remote.request.RegisterRequest
import android.compose.data.remote.response.LoginResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody

interface AuthRepository {
    suspend fun loginUser(loginRequest: LoginRequest): Flow<Resource<LoginResponse>>
    suspend fun registerUser(registerRequest: RegisterRequest): Flow<Resource<Any>>
    suspend fun logoutUser()

    suspend fun changePasswordFinish(passwordRequest: ForgotPasswordRequest): Flow<Resource<Any>>

    suspend fun changePasswordInit(emailAddress: RequestBody): Flow<Resource<Any>>

    suspend fun changePassword(token: String, changePasswordRequest: ChangePasswordRequest): Flow<Resource<Any>>
}