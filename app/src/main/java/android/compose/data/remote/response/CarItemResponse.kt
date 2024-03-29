package android.compose.data.remote.response

import android.compose.data.local.CarEntity

data class CarItemResponse(
    val body: String,
    val brand: String,
    val engineSize: Int,
    val fuel: String,
    val id: Int,
    val inspections: String?,
    val licensePlate: String,
    val model: String,
    val modelYear: Int,
    val nrOfSeats: Int,
    val options: String,
    val price: Double,
    val rentals: String?,
    val repairs: String?,
    val since: String
)

fun CarItemResponse.toCarEntity(): CarEntity {
    return CarEntity(
        id = this.id,
        body = this.body,
        brand = this.brand,
        engineSize = this.engineSize,
        fuel = this.fuel,
        inspections = this.inspections ?: "",
        licensePlate = this.licensePlate,
        model = this.model,
        modelYear = this.modelYear,
        nrOfSeats = this.nrOfSeats,
        options = this.options,
        price = this.price,
        rentals = this.rentals ?: "",
        repairs = this.repairs ?: "",
        since = this.since
    )
}
