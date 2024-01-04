package android.compose.presentation.viewmodels.cars

import android.compose.data.local.AuthPreferences
import android.compose.data.remote.response.CarItemResponse
import android.compose.data.repository.cars.CarsRepository
import android.compose.util.Resource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest

class CarDetailViewModel(
    private val carsRepository: CarsRepository,
    private val authPreferences: AuthPreferences
): ViewModel() {

    private val _carDetails = MutableStateFlow<Resource<CarItemResponse>?>(null)
    val carDetails = _carDetails.asStateFlow()

    fun fetchCarDetails(carId: String) {
        viewModelScope.launch {
            val token = authPreferences.getTokenFlow().firstOrNull()
            if (!token.isNullOrBlank()) {
                carsRepository.getCarDetails("$token", carId).collectLatest { result ->
                    _carDetails.value = result
                }
            } else {
                _carDetails.value = Resource.Error("Authorization token is missing")
            }
        }
    }
}


