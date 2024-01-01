package android.compose.domain.use_cases

import android.compose.data.remote.request.RegisterRequest
import android.compose.data.repository.auth.AuthRepository
import android.compose.domain.results.RegisterResult

class RegisterUseCase (
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        username:String,
        password:String,
        confirmPassword:String,
        email:String,
    ): RegisterResult {

        val usernameError = if (username.isBlank()) "Username cannot be blank" else null
        val emailError = if (email.isBlank()) "Email cannot be blank" else null
        val passwordError = if (password.isBlank()) "Password cannot be blank" else null
        val confirmPasswordError = if (confirmPassword.isBlank()) "Password cannot be blank" else null

        val registerResult = RegisterResult()

        if (usernameError != null || emailError != null || passwordError != null || confirmPasswordError != null){
            registerResult.usernameError = usernameError
            registerResult.emailError = emailError
            registerResult.passwordError = passwordError
            registerResult.confirmPasswordError = confirmPasswordError

            return registerResult
        }

        val registerRequest = RegisterRequest(username = username, email = email, password = password, langKey = "nl")

        registerResult.result = repository.registerUser(registerRequest)

        return registerResult
    }
}