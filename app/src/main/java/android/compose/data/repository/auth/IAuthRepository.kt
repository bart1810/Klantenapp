package android.compose.data.repository.auth

import android.compose.util.Resource
import android.compose.data.remote.AutoMaatApi
import android.compose.data.local.AuthPreferences
import android.compose.data.remote.request.LoginRequest
import android.compose.data.remote.request.RegisterRequest
import android.compose.data.remote.response.LoginResponse
import android.compose.data.remote.response.RegisterResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class IAuthRepository(
    private val autoMaatApi: AutoMaatApi,
    private val preferences: AuthPreferences
): AuthRepository {
    override suspend fun loginUser(loginRequest: LoginRequest): Flow<Resource<LoginResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = autoMaatApi.loginUser(loginRequest)
            preferences.saveToken(response.token)
            emit(Resource.Success(response))
        } catch (e: IOException) {
            e.printStackTrace()
            emit(Resource.Error("${e.message}"))
        } catch (e: HttpException) {
            e.printStackTrace()
            emit(Resource.Error("${e.message}"))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error("${e.message}"))
        }
    }

    override suspend fun logoutUser() {
        if (!preferences.getTokenFlow().firstOrNull().isNullOrEmpty()) {
            preferences.deleteToken()
        } else {
            return
        }
    }

    override suspend fun registerUser(registerRequest: RegisterRequest): Flow<Resource<Any>> = flow {
        try {
            emit(Resource.Loading())
            autoMaatApi.registerUser(registerRequest)
            emit(Resource.Success())
        } catch (e: IOException) {
            e.printStackTrace()
            emit(Resource.Error("${e.message}"))
        } catch (e: HttpException) {
            e.printStackTrace()
            emit(Resource.Error("${e.message}"))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error("${e.message}"))
        }
    }
}