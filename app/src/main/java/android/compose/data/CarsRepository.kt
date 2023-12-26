package android.compose.data

import android.compose.Resource
import android.compose.data.model.CarsItem
import kotlinx.coroutines.flow.Flow

interface CarsRepository {
    suspend fun getCarsList(): Flow<Resource<List<CarsItem>>>
}