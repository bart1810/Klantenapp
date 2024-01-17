package android.compose.data.repository.customer

import android.compose.util.Resource
import android.compose.data.remote.response.CarItemResponse
import android.compose.data.remote.response.CustomerResponse
import kotlinx.coroutines.flow.Flow

interface CustomerRepository {
    suspend fun getCustomer(token: String, customerId: String): Flow<Resource<CustomerResponse>>
}