package android.compose.data.repository.auth

import android.compose.util.Resource
import android.compose.data.remote.AutoMaatApi
import android.compose.data.local.AuthPreferences
import android.compose.data.remote.request.LoginRequest
import android.compose.data.remote.request.RegisterRequest
import android.compose.data.remote.response.CarItemResponse
import android.compose.data.remote.response.LoginResponse
import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class IAuthRepository(
    private val autoMaatApi: AutoMaatApi,
    private val preferences: AuthPreferences
): AuthRepository {
    override suspend fun loginUser(loginRequest: LoginRequest): Resource<Unit> {
        return try {
            val response = autoMaatApi.loginUser(loginRequest)
            Log.d("response", response.toString())
            preferences.saveToken(response.token)
            Resource.Success(Unit)
        }catch (e: IOException){
            Resource.Error("${e.message}")
        }catch (e: HttpException){
            Resource.Error("${e.message}")
        }
    }

    override suspend fun authenticateUser(token: String): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())
            val authenticated = autoMaatApi.authenticateUser("Bearer $token")
            emit(Resource.Success(authenticated))
        } catch (e: IOException) {
            e.printStackTrace()
            emit(Resource.Error("Network error: Could not authenticate"))
        } catch (e: HttpException) {
            e.printStackTrace()
            emit(Resource.Error("HTTP error: Could not authenticate"))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error("Unknown error occurred"))
        }
    }

    override suspend fun registerUser(registerRequest: RegisterRequest): Resource<Unit> {
        return try {
            val response = autoMaatApi.registerUser(registerRequest)
            Resource.Success(Unit)
        }catch (e: IOException){
            Resource.Error("${e.message}")
        }catch (e: HttpException){
            Resource.Error("${e.message}")
        }
    }
}