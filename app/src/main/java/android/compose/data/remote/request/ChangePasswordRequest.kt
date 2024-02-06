package android.compose.data.remote.request

import com.google.gson.annotations.SerializedName

data class ChangePasswordRequest(
    @SerializedName("currentPassword")
    var currentPassword: String,
    @SerializedName("newPassword")
    var newPassword: String,
)
