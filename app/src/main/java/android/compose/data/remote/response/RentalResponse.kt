package android.compose.data.remote.response

import android.compose.data.remote.objects.RentalState

data class RentalResponse(
    val id: Int,
    val code: String,
    val longitude: Float,
    val latitude: Float,
    val fromDate: String,
    val toDate: String,
    val state: RentalState,
    val inspections: List<Any>?,
    val customer: CustomerResponse?,
    val car: CarItemResponse?
)
