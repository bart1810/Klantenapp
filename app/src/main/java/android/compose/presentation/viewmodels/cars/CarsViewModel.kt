package android.compose.presentation.viewmodels.cars

import android.compose.util.Resource
import android.compose.data.repository.cars.CarsRepository
import android.compose.data.remote.response.CarItemResponse
import android.compose.ui.components.ToastMessage
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CarsViewModel(
    private val carsRepository: CarsRepository
): ViewModel() {
    var selectedBrandFilters = MutableStateFlow<List<String>>(emptyList())
    var selectedFuelTypes = MutableStateFlow<List<String>>(emptyList())
    var selectedBodyTypes = MutableStateFlow<List<String>>(emptyList())

    private val _noCarsFound = MutableStateFlow(false)
    val noCarsFound = _noCarsFound.asStateFlow()

    private val _cars = MutableStateFlow<List<CarItemResponse>>(emptyList())
    val cars = _cars.asStateFlow()

    private val _allCars = MutableStateFlow<List<CarItemResponse>>(emptyList())
    val allCars = _allCars.asStateFlow()

    private val _showErrorToastChannel = Channel<ToastMessage>()
    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()

    init {
        fetchAllCars()
    }

    private fun fetchAllCars() {
        viewModelScope.launch {
            carsRepository.getCarsList().collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { carsList ->
                            _allCars.value = carsList
                            _cars.emit(carsList)
                        }
                    }
                    is Resource.Error -> {
                        _showErrorToastChannel.send(ToastMessage.GenericError)
                    }
                    is Resource.Loading -> {
                        TODO()
                    }
                }
            }
        }
    }

    fun updateBrandFilters(brands: List<String>) {
        selectedBrandFilters.value = brands
        applyFilters()
    }

    fun updateFuelFilters(fuels: List<String>) {
        selectedFuelTypes.value = fuels
        applyFilters()
    }

    fun updateBodyFilters(bodies: List<String>) {
        selectedBodyTypes.value = bodies
        applyFilters()
    }

    fun applyFilters() {
        viewModelScope.launch {
            val filteredCars = _allCars.value.filter { car ->
                (selectedBrandFilters.value.isEmpty() || car.brand in selectedBrandFilters.value) &&
                        (selectedFuelTypes.value.isEmpty() || car.fuel in selectedFuelTypes.value) &&
                        (selectedBodyTypes.value.isEmpty() || car.body in selectedBodyTypes.value)
            }

            if (filteredCars.isEmpty()) {
                _showErrorToastChannel.send(ToastMessage.NoCarsFound)
            } else {
                _cars.emit(filteredCars)
            }
        }
    }

    fun resetFilters() {
        viewModelScope.launch {
            _cars.emit(_allCars.value)
        }
    }
}