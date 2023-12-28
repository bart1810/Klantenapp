package android.compose.data.remote.request

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("username")
    var username: String,
    @SerializedName("password")
    var password: String,
    @SerializedName("rememberMe")
    var rememberMe: Boolean
)
