package android.compose.viewmodels

import android.compose.Resource
import android.compose.data.cars.CarsRepository
import android.compose.models.CarItem
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CarsViewModel(
    private val carsRepository: CarsRepository
): ViewModel() {

    private val _cars = MutableStateFlow<List<CarItem>>(emptyList())
    val cars = _cars.asStateFlow()

    private val _showErrorToastChannel = Channel<Boolean>()
    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            carsRepository.getCarsList().collectLatest { result ->
                when(result) {
                    is Resource.Error -> {
                        _showErrorToastChannel.send(true)
                    }
                    is Resource.Success -> {
                        result.data?.let { cars ->
                            _cars.update { cars }
                        }
                    }
                    is Resource.Loading -> TODO()
                }
            }
        }
    }
}