package android.compose.presentation.viewmodels.customer

import android.compose.data.local.AuthPreferences
import android.compose.data.remote.response.CustomerResponse
import android.compose.data.repository.customer.CustomerRepository
import android.compose.util.Resource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class CustomerViewModel(
    private val customerRepository: CustomerRepository,
    private val authPreferences: AuthPreferences
): ViewModel() {

    private val _customerDetails = MutableStateFlow<Resource<CustomerResponse>?>(null)
    val customerDetails = _customerDetails.asStateFlow()

    fun fetchCustomer() {
        viewModelScope.launch {
            val token = authPreferences.getTokenFlow().firstOrNull()
            if (!token.isNullOrBlank()) {
                customerRepository.getCustomer("$token").collectLatest { result ->
                    _customerDetails.value = result
                }
            } else {
                _customerDetails.value = Resource.Error("Authorization token or customer id is missing")
            }
        }
    }
}