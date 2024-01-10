package android.compose.data.repository.account

import android.compose.util.Resource
import android.compose.data.remote.response.AccountResponse
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    suspend fun getAccount(token: String): Flow<Resource<AccountResponse>>
}