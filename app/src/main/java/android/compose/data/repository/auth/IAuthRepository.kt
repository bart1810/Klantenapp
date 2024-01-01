package android.compose.data.repository.auth

import android.compose.util.Resource
import android.compose.data.remote.AutoMaatApi
import android.compose.data.local.AuthPreferences
import android.compose.data.remote.request.LoginRequest
import android.compose.data.remote.request.RegisterRequest
import android.util.Log
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
            preferences.saveAuthToken(response.token)
            Resource.Success(Unit)
        }catch (e: IOException){
            Resource.Error("${e.message}")
        }catch (e: HttpException){
            Resource.Error("${e.message}")
        }
    }

    override suspend fun registerUser(registerRequest: RegisterRequest): Resource<Unit> {
        TODO("Not yet implemented")
    }
}