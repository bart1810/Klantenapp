package android.compose.data.remote.request

import com.google.gson.annotations.SerializedName

data class ForgotPasswordRequest(
    @SerializedName("key")
    var key: String,
    @SerializedName("newPassword")
    var newPassword: String,
)
