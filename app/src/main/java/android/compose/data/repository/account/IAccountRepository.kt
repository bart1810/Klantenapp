package android.compose.data.repository.account

import android.compose.util.Resource
import android.compose.data.remote.AutoMaatApi
import android.compose.data.remote.response.AccountResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class IAccountRepository(
    private val autoMaatApi: AutoMaatApi,
): AccountRepository {

    override suspend fun getAccount(token: String): Flow<Resource<AccountResponse>> = flow {
        try {
            emit(Resource.Loading())
            val accountResponse = autoMaatApi.getAccount("Bearer $token")
            emit(Resource.Success(accountResponse))
        } catch (e: IOException) {
            e.printStackTrace()
            emit(Resource.Error("Network error: Could not load account"))
        } catch (e: HttpException) {
            e.printStackTrace()
            emit(Resource.Error("HTTP error: Could not load account"))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error("Unknown error occurred"))
        }
    }
}