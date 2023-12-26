package android.compose.data

import android.compose.models.CarItem
import android.compose.models.LoginRequest
import android.compose.models.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AutoMaatApi {
    companion object {
        const val BASE_URL = "https://2be1-2001-1c01-4705-3a00-75c3-16ed-b7da-5f56.ngrok-free.app"
    }

    @POST("/api/authenticate")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @GET("/api/cars")
    suspend fun getAllCars(): List<CarItem>

}