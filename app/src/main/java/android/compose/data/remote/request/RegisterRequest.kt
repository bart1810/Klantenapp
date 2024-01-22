package android.compose.data.remote.request

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("login")
    var login: String,
    @SerializedName("email")
    var email: String,
    @SerializedName("langKey")
    var langKey: String,
    @SerializedName("password")
    var password: String,
    @SerializedName("activated")
    var activated: Boolean,
)
