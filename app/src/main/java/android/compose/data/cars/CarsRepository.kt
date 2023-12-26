package android.compose.data.cars

import android.compose.Resource
import android.compose.models.CarItem
import kotlinx.coroutines.flow.Flow

interface CarsRepository {
    suspend fun getCarsList(): Flow<Resource<List<CarItem>>>
}