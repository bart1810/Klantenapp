package android.compose.models

data class CarItem(
    val body: String,
    val brand: String,
    val engineSize: Int,
    val fuel: String,
    val id: Int,
    val inspections: Any,
    val licensePlate: String,
    val model: String,
    val modelYear: Int,
    val nrOfSeats: Int,
    val options: String,
    val price: Double,
    val rentals: Any,
    val repairs: Any,
    val since: String
)