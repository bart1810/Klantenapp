package android.compose.data.repository.cars

import android.compose.util.Resource
import android.compose.data.remote.response.CarItemResponse
import kotlinx.coroutines.flow.Flow

interface CarsRepository {
    suspend fun getCarsList(): Flow<Resource<List<CarItemResponse>>>
}