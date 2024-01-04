package android.compose.data.repository.cars

import android.compose.util.Resource
import android.compose.data.remote.AutoMaatApi
import android.compose.data.remote.response.CarItemResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class CarsRepositoryImplementation(
    private val autoMaatApi: AutoMaatApi
): CarsRepository {

    override suspend fun getCarsList(): Flow<Resource<List<CarItemResponse>>> {
        return flow {
            val carsFromAutoMaatApi = try {
                autoMaatApi.getAllCars()
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Error loading cars"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Error loading cars"))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error("Error loading cars"))
                return@flow
            }

            emit(Resource.Success(carsFromAutoMaatApi))
        }
    }

    override suspend fun getCarDetails(token: String, carId: String): Flow<Resource<CarItemResponse>> = flow {
        try {
            emit(Resource.Loading())
            val carDetails = autoMaatApi.getCarDetails("Bearer $token", carId)
            emit(Resource.Success(carDetails))
        } catch (e: IOException) {
            e.printStackTrace()
            emit(Resource.Error("Network error: Could not load car details"))
        } catch (e: HttpException) {
            e.printStackTrace()
            emit(Resource.Error("HTTP error: Could not load car details"))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error("Unknown error occurred"))
        }
    }
}