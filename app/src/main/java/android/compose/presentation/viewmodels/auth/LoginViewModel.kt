package android.compose.presentation.viewmodels.auth

import android.compose.common.UiEvents
import android.compose.data.remote.request.LoginRequest
import android.compose.data.remote.response.LoginResponse
import android.compose.data.repository.auth.AuthRepository
import android.compose.util.Resource
import androidx.lifecycle.ViewModel
import android.compose.presentation.viewmodels.states.CheckboxState
import android.compose.presentation.viewmodels.states.TextFieldState
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel@Inject constructor(
    private val authRepository: AuthRepository,
): ViewModel() {

    private val _loginState = MutableStateFlow<Resource<LoginResponse>?>(null)
    private val loginState = _loginState.asStateFlow()

    private val _usernameState = mutableStateOf(TextFieldState())
    val usernameState: State<TextFieldState> = _usernameState

    private val _passwordState = mutableStateOf(TextFieldState())
    val passwordState: State<TextFieldState> = _passwordState

    private val _rememberMeState = mutableStateOf(CheckboxState())
    val rememberMeState: State<CheckboxState> = _rememberMeState

    fun setUsername(value:String){
        _usernameState.value = usernameState.value.copy(text = value)
    }

    fun setPassword(value:String){
        _passwordState.value = passwordState.value.copy(text = value)
    }

    fun setRememberMe(value:Boolean){
        _rememberMeState.value = rememberMeState.value.copy(checked = value)
    }

    private val  _eventFlow = MutableSharedFlow<UiEvents>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun loginUser() {
        viewModelScope.launch {
            val loginRequest = LoginRequest(
                username = usernameState.value.text,
                password = passwordState.value.text,
                rememberMe = rememberMeState.value.checked
            )

            authRepository.loginUser(loginRequest).collectLatest { result ->
                _loginState.value = result
            }

            when (loginState.value) {
                is Resource.Success -> {
                    _eventFlow.emit(
                        UiEvents.NavigateEvent(
                            loginState.value?.message ?: "Success!"
                        )
                    )
                }

                is Resource.Error -> {
                    val message = "Gebruikersnaam of wachtwoord is onjuist"

                    _eventFlow.emit(
                        UiEvents.SnackbarEvent(
                            message
                        )
                    )
                }

                else -> {
                }
            }
        }
    }
    fun logoutUser() {
        viewModelScope.launch {
            authRepository.logoutUser()
        }
    }
}