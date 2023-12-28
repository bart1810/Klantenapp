package android.compose.domain.use_cases

import android.compose.data.repository.auth.AuthRepository
import android.compose.data.remote.request.LoginRequest
import android.compose.domain.results.LoginResult

class LoginUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        username:String,
        password:String,
        rememberMe:Boolean
    ): LoginResult {

        val usernameError = if (username.isBlank()) "Username cannot be blank" else null
        val passwordError = if (password.isBlank()) "Password cannot be blank" else null

        if (usernameError != null){
            return LoginResult(
                usernameError = usernameError
            )
        }

        if (passwordError!=null){
            return LoginResult(
                passwordError = passwordError
            )
        }

        val loginRequest = LoginRequest(
            username = username,
            password = password,
            rememberMe = rememberMe
        )

        return LoginResult(
            result = repository.loginUser(loginRequest)
        )
    }
}