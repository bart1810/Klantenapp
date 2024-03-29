package android.compose.data.remote.response

data class AccountResponse(
    val id: Int,
    val login: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val imageUrl: String,
    val activated: Boolean,
    val langKey: String,
    val createdBy: String,
    val createdDate: String,
    val lastModifiedBy: String,
    val lastModifiedDate: String,
    val authorities: ArrayList<String> = arrayListOf()
)
