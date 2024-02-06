package android.compose

import android.compose.data.remote.response.CarItemResponse
import android.compose.data.repository.cars.CarsRepository
import android.compose.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeCarsRepository : CarsRepository {
    private val carsData = listOf(
        CarItemResponse(
            id = 1,
            brand = "Toyota",
            body = "Sedan",
            engineSize = 1800,
            fuel = "Petrol",
            inspections = "2021-11-11",
            licensePlate = "XYZ 1234",
            model = "Camry",
            modelYear = 2021,
            nrOfSeats = 4,
            options = "Air Conditioning, Alloy Wheels",
            price = 25000.00,
            rentals = "2021-11-12",
            repairs = "2021-11-13",
            since = "2018-11-11"
        ),
        CarItemResponse(
            id = 2,
            brand = "Honda",
            body = "Hatchback",
            engineSize = 1500,
            fuel = "Diesel",
            inspections = "2021-10-11",
            licensePlate = "XYZ 5678",
            model = "Civic",
            modelYear = 2020,
            nrOfSeats = 4,
            options = "Sunroof, Heated Seats",
            price = 22000.00,
            rentals = "2021-10-12",
            repairs = "2021-10-13",
            since = "2017-10-11"
        ),
        CarItemResponse(
            id = 3,
            brand = "Ford",
            body = "SUV",
            engineSize = 2000,
            fuel = "Electric",
            inspections = "2021-09-11",
            licensePlate = "XYZ 9012",
            model = "Explorer",
            modelYear = 2022,
            nrOfSeats = 6,
            options = "4 Wheel Drive, Bluetooth",
            price = 40000.00,
            rentals = "2021-09-12",
            repairs = "2021-09-13",
            since = "2019-09-11"
        )
    )

    override suspend fun getCarsList(): Flow<Resource<List<CarItemResponse>>> {
        return flow {
            emit(Resource.Success(carsData))
        }
    }

    override suspend fun getCarDetails(
        token: String,
        carId: String
    ): Flow<Resource<CarItemResponse>> {
        TODO("Not yet implemented")
    }

    // Implement other methods if needed
}
