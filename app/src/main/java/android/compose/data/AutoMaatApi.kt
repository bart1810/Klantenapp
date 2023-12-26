package android.compose.data

import android.compose.data.model.CarsItem
import retrofit2.http.GET

interface AutoMaatApi {

    @GET("/api/cars")
    suspend fun getAllCars(): List<CarsItem>

    companion object {
        const val BASE_URL = "https://dear-snapper-highly.ngrok-free.app"
    }
}