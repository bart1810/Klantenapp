package android.compose.domain.results

import android.compose.util.Resource

data class RegisterResult (
    var passwordError: String? = null,
    var confirmPasswordError: String? = null,
    var usernameError : String? = null,
    var emailError : String? = null,
    var result: Resource<Unit>? = null
)
