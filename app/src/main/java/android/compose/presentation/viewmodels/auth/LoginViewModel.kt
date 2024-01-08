package android.compose.presentation.viewmodels.auth

import android.compose.common.UiEvents
import android.compose.data.local.AuthPreferences
import android.compose.data.remote.response.CarItemResponse
import android.compose.data.repository.auth.AuthRepository
import android.compose.data.repository.cars.CarsRepository
import android.compose.util.Resource
import androidx.lifecycle.ViewModel
import android.compose.domain.use_cases.LoginUseCase
import android.compose.presentation.viewmodels.states.CheckboxState
import android.compose.presentation.viewmodels.states.AuthState
import android.compose.presentation.viewmodels.states.TextFieldState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel@Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val authPreferences: AuthPreferences,
    private val authRepository: AuthRepository,
): ViewModel() {


    private var _loginState = mutableStateOf(AuthState())
    private val loginState: State<AuthState> = _loginState

    private val _usernameState = mutableStateOf(TextFieldState())
    val usernameState: State<TextFieldState> = _usernameState

    fun setUsername(value:String){
        _usernameState.value = usernameState.value.copy(text = value)
    }

    fun setPassword(value:String){
        _passwordState.value = passwordState.value.copy(text = value)
    }

    fun setRememberMe(value:Boolean){
        _rememberMeState.value = rememberMeState.value.copy(checked = value)
    }

    private val _passwordState = mutableStateOf(TextFieldState())
    val passwordState: State<TextFieldState> = _passwordState

    private val _rememberMeState = mutableStateOf(CheckboxState())
    val rememberMeState: State<CheckboxState> = _rememberMeState

    private val  _eventFlow = MutableSharedFlow<UiEvents>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _authenticated = MutableStateFlow<Resource<String>?>(null)
    val authenticated = _authenticated.asStateFlow()

    fun loginUser(){
        viewModelScope.launch {
            _loginState.value = loginState.value.copy(isLoading = true)

            val loginResult = loginUseCase(
                username = usernameState.value.text,
                password = passwordState.value.text,
                rememberMe = rememberMeState.value.checked
            )

            _loginState.value = loginState.value.copy(isLoading = false)

            if (loginResult.usernameError != null){
                _usernameState.value=usernameState.value.copy(error = loginResult.usernameError)
            }
            if (loginResult.passwordError != null){
                _passwordState.value = passwordState.value.copy(error = loginResult.passwordError)
            }

            when(loginResult.result){
                is Resource.Success->{
                    _eventFlow.emit(
                        UiEvents.NavigateEvent(
                            loginResult.result.message ?: "Success!"
                        )
                    )
                }
                is Resource.Error->{
                    UiEvents.SnackbarEvent(
                        loginResult.result.message ?: "Error!"
                    )
                }
                else -> {

                }
            }
        }
    }

    fun isUserAuthenticated() {
        viewModelScope.launch {
            val token = authPreferences.getTokenFlow().firstOrNull()
            if (!token.isNullOrBlank()) {
                authRepository.authenticateUser("$token").collectLatest { result ->
                    _authenticated.value = result
                }
            } else {
                _authenticated.value = Resource.Error("Authorization token is missing")
            }
        }
    }
}