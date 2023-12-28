package android.compose.data.remote

import android.compose.data.remote.response.CarItemResponse
import android.compose.data.remote.request.LoginRequest
import android.compose.data.remote.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AutoMaatApi {
    companion object {
        const val BASE_URL = "https://22b7-2001-1c01-4705-3a00-75c3-16ed-b7da-5f56.ngrok-free.app"
    }

    @POST("/api/authenticate")
    suspend fun loginUser(@Body loginRequest: LoginRequest): LoginResponse

    @GET("/api/cars")
    suspend fun getAllCars(): List<CarItemResponse>

}