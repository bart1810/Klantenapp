package android.compose.data.remote.response

import android.compose.data.remote.objects.SystemUser

data class CustomerResponse(
    val id: Int,
    val nr: Int?,
    val lastName: String?,
    val firstName: String?,
    val from: String?,
    val systemUser: SystemUser,
    val rentals: List<RentalResponse>?,
    val location: Any?,
)