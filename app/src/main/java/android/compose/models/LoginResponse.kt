package android.compose.models

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("id_token")
    var token: String,
)
