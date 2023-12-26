package android.compose.data

import android.compose.Resource
import android.compose.data.model.CarsItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class CarsRepositoryImplementation(
    private val autoMaatApi: AutoMaatApi
):  CarsRepository {

    override suspend fun getCarsList(): Flow<Resource<List<CarsItem>>> {
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
}