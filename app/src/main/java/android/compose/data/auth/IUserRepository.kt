package android.compose.data.auth

import android.compose.Resource
import android.compose.data.AutoMaatApi
import android.compose.models.LoginRequest
import android.compose.models.LoginResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class IUserRepository(
    private val autoMaatApi: AutoMaatApi
): UserRepository {
    override suspend fun loginUser(loginRequest: LoginRequest): Flow<Resource<LoginResponse>> {
        return flow {
            val loginFromApi = try {
                autoMaatApi.loginUser(loginRequest)
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

            emit(Resource.Success(loginFromApi.body()))
        }
    }
//        return  autoMaatApi.loginUser(loginRequest = loginRequest)
}