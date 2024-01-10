package android.compose.presentation.viewmodels.auth

import android.compose.common.UiEvents
import android.compose.data.local.AuthPreferences
import android.compose.data.repository.auth.AuthRepository
import android.compose.util.Resource
import androidx.lifecycle.ViewModel
import android.compose.domain.use_cases.LoginUseCase
import android.compose.presentation.viewmodels.cars.CarDetailViewModel
import android.compose.presentation.viewmodels.states.CheckboxState
import android.compose.presentation.viewmodels.states.AuthState
import android.compose.presentation.viewmodels.states.TextFieldState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
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
}