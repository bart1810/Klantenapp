package android.compose.data.remote.request

data class InspectionRequest(
    val code: String,
    val completed: String,
    val odometer: Int,
    val photo: ByteArray,
    val photoContentType: String,
    val result: String
)