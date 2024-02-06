package android.compose.data.remote.objects

data class SystemUser(
    val createdBy: String,
    val createdDate: String,
    val lastModifiedBy: String,
    val lastModifiedDate: String,
    val id: Int,
    val login: String,
    val firstName: String?,
    val lastName: String?,
    val email: String,
    val activated: Boolean,
    val langKey: String,
    val imageUrl: String?,
    val resetDate: String?
)
