package android.compose.presentation.viewmodels.auth

import android.compose.common.UiEvents
import android.compose.util.Resource
import androidx.lifecycle.ViewModel
import android.compose.domain.use_cases.RegisterUseCase
import android.compose.presentation.viewmodels.states.AuthState
import android.compose.presentation.viewmodels.states.TextFieldState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel@Inject constructor(
    private val registerUseCase: RegisterUseCase,
): ViewModel() {

    private var _registerState = mutableStateOf(AuthState())
    val registerState: State<AuthState> = _registerState

    private val _usernameState = mutableStateOf(TextFieldState())
    val usernameState: State<TextFieldState> = _usernameState

    private val _emailState = mutableStateOf(TextFieldState())
    val emailState: State<TextFieldState> = _emailState

    private val _passwordState = mutableStateOf(TextFieldState())
    val passwordState: State<TextFieldState> = _passwordState

    private val _confirmPasswordState = mutableStateOf(TextFieldState())
    val confirmPasswordState: State<TextFieldState> = _confirmPasswordState

    fun setUsername(value:String){
        _usernameState.value = usernameState.value.copy(text = value)
    }

    fun setEmail(value:String){
        _emailState.value = emailState.value.copy(text = value)
    }

    fun setPassword(value:String){
        _passwordState.value = passwordState.value.copy(text = value)
    }

    fun setConfirmPassword(value:String){
        _confirmPasswordState.value = confirmPasswordState.value.copy(text = value)
    }

    private val  _eventFlow = MutableSharedFlow<UiEvents>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun registerUser(){
        viewModelScope.launch {
            _registerState.value = registerState.value.copy(isLoading = false)

            val registerResult = registerUseCase(
                username = usernameState.value.text,
                email = emailState.value.text,
                password = passwordState.value.text,
                confirmPassword = confirmPasswordState.value.text,
            )

            _registerState.value = registerState.value.copy(isLoading = false)

            if (registerResult.usernameError != null){
                _usernameState.value=usernameState.value.copy(error = registerResult.usernameError)
            }
            if (registerResult.passwordError != null){
                _passwordState.value = passwordState.value.copy(error = registerResult.passwordError)
            }

            when(registerResult.result){
                is Resource.Success->{
                    _eventFlow.emit(
                        UiEvents.SnackbarEvent("Succes")
                    )
                }
                is Resource.Error->{
                    _eventFlow.emit(
                        UiEvents.SnackbarEvent("Failed")
                    )
                }
                else -> {

                }
            }
        }
    }
}