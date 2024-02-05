package android.compose.data.repository.damageForm

import android.compose.data.remote.AutoMaatApi
import android.compose.data.remote.request.InspectionRequest
import android.compose.util.Resource
import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class DamageFormRepositoryImplementation(
    private val autoMaatApi: AutoMaatApi
): DamageFormRepository {

    override suspend fun sendDamageForm(token: String, inspectionRequest: InspectionRequest): Flow<Resource<Any>> = flow {
        try {
            emit(Resource.Loading())
            val response = autoMaatApi.sendInspection(token, inspectionRequest)
            Log.d("DamageFormRepositoryImplementation", "sent request in DFRImp")
            emit(Resource.Success(response))
        } catch (e: IOException) {
            e.printStackTrace()
            emit(Resource.Error("${e.message}"))
        } catch (e: HttpException) {
            e.printStackTrace()
            emit(Resource.Error("${e.message}"))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error("${e.message}"))
        }
    }

}