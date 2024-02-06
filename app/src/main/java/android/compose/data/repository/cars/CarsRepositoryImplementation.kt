package android.compose.data.repository.cars

import android.compose.data.CarDao
import android.compose.data.local.toCarItemResponse
import android.compose.util.Resource
import android.compose.data.remote.AutoMaatApi
import android.compose.data.remote.response.CarItemResponse
import android.compose.data.remote.response.toCarEntity
import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CarsRepositoryImplementation @Inject constructor(
    private val autoMaatApi: AutoMaatApi,
    private val carDao: CarDao
): CarsRepository {

    override suspend fun getCarsList(): Flow<Resource<List<CarItemResponse>>> = flow {
        emit(Resource.Loading())

        try {
            val localCars = carDao.getAllCars().firstOrNull()
            if (!localCars.isNullOrEmpty()) {
                emit(Resource.Success(localCars.map { it.toCarItemResponse() }))
            }

            val remoteCars = autoMaatApi.getAllCars()

            carDao.deleteAllCars()
            carDao.insertAll(remoteCars.map { it.toCarEntity() })
            emit(Resource.Success(carDao.getAllCars().first().map { it.toCarItemResponse() }))
        } catch (e: IOException) {
            e.printStackTrace()
            emit(Resource.Error("Network error: Could not load cars"))

            val cachedCars = carDao.getAllCars().firstOrNull()
            if (!cachedCars.isNullOrEmpty()) {
                emit(Resource.Success(cachedCars.map { it.toCarItemResponse() }))
            } else {
                emit(Resource.Error("Error loading cars"))
            }
        } catch (e: HttpException) {
            e.printStackTrace()
            emit(Resource.Error("HTTP error: Could not load cars"))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error("Unknown error occurred"))
        }
    }

    override suspend fun getCarDetails(token: String, carId: String): Flow<Resource<CarItemResponse>> = flow {
        emit(Resource.Loading())

        val localCarDetails = carDao.getCarById(carId.toInt()).firstOrNull()
        if (localCarDetails != null) {
            emit(Resource.Success(localCarDetails.toCarItemResponse()))
            return@flow
        }

        try {
            val carDetails = autoMaatApi.getCarDetails("Bearer $token", carId)
            Log.d("carDetails", carDetails.toString())

            carDao.insert(carDetails.toCarEntity())

            emit(Resource.Success(carDetails))
        } catch (e: IOException) {
            e.printStackTrace()
            if (localCarDetails != null) {
                emit(Resource.Success(localCarDetails.toCarItemResponse()))
            } else {
                emit(Resource.Error("Network error: Could not load car details"))
            }
        } catch (e: HttpException) {
            e.printStackTrace()
            emit(Resource.Error("HTTP error: Could not load car details"))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error("Unknown error occurred"))
        }
    }

}