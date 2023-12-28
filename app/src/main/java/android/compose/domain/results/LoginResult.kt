package android.compose.domain.results

import android.compose.util.Resource

data class LoginResult(
    val passwordError: String? = null,
    val usernameError : String? = null,
    val result: Resource<Unit>? = null
)
