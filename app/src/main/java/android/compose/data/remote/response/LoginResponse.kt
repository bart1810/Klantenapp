package android.compose.data.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("id_token")
    var token: String,
)
