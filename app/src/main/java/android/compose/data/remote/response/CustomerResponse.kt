package android.compose.data.remote.response

data class CustomerResponse(
    val id: Int,
    val nr: Int,
    val lastName: String,
    val firstName: String,
    val from: String,
    val rentals: Any,
    val location: Any,
)