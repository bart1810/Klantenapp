package android.compose.data.remote

import android.compose.data.remote.response.CarItemResponse
import android.compose.data.remote.request.LoginRequest
import android.compose.data.remote.request.RegisterRequest
import android.compose.data.remote.response.AccountResponse
import android.compose.data.remote.response.LoginResponse
import android.compose.data.remote.response.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface AutoMaatApi {
    companion object {
        const val BASE_URL = "https://5db3-145-33-101-69.ngrok-free.app"
    }

    @POST("/api/authenticate")
    suspend fun loginUser(@Body loginRequest: LoginRequest): LoginResponse

    @POST("/api/AM/register")
    suspend fun registerUser(@Body registerRequest: RegisterRequest): RegisterResponse

    @GET("/api/cars")
    suspend fun getAllCars(): List<CarItemResponse>

    @GET("/api/cars/{carId}")
    suspend fun getCarDetails(
        @Header("Authorization") token: String,
        @Path("carId") carId: String): CarItemResponse

    @GET("/api/AM/account")
    suspend fun getAccount(@Header("Authorization") token: String): AccountResponse
}
