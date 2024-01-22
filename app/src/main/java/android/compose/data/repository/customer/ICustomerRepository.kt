package android.compose.data.repository.customer

import android.compose.util.Resource
import android.compose.data.remote.AutoMaatApi
import android.compose.data.remote.response.CustomerResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class ICustomerRepository(
    private val autoMaatApi: AutoMaatApi
): CustomerRepository {

    override suspend fun getCustomer(token: String): Flow<Resource<CustomerResponse>> {
        return flow {
            val customerFromApi = try {
                emit(Resource.Loading())
                autoMaatApi.getCustomer("Bearer $token")
            } catch (e: IOException) {
                emit(Resource.Loading())
                e.printStackTrace()
                emit(Resource.Error("Error loading customer"))
                return@flow
            } catch (e: HttpException) {
                emit(Resource.Loading())
                e.printStackTrace()
                emit(Resource.Error("Error loading customer"))
                return@flow
            } catch (e: Exception) {
                emit(Resource.Loading())
                e.printStackTrace()
                emit(Resource.Error("Error loading customer"))
                return@flow
            }

            emit(Resource.Success(customerFromApi))
        }
    }
}