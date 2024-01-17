package android.compose.presentation.viewmodels.auth

import android.compose.common.UiEvents
import android.compose.data.remote.request.RegisterRequest
import android.compose.data.remote.response.RegisterResponse
import android.compose.data.repository.auth.AuthRepository
import android.compose.util.Resource
import androidx.lifecycle.ViewModel
import android.compose.presentation.viewmodels.states.TextFieldState
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
class RegisterViewModel@Inject constructor(
    private val authRepository: AuthRepository,
): ViewModel() {

    private val _registerState = MutableStateFlow<Resource<RegisterResponse>?>(null)
    private val registerState = _registerState.asStateFlow()

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
            val registerRequest = RegisterRequest (
                login = usernameState.value.text,
                password = passwordState.value.text,
                email = emailState.value.text,
                langKey = "en"
            )

            authRepository.registerUser(registerRequest).collectLatest { result ->
                _registerState.value = result
            }

            when(registerState.value){
                is Resource.Success->{
                    _eventFlow.emit(
                        UiEvents.NavigateEvent(
                            registerState.value?.message ?: "Success!"
                        )
                    )
                }
                is Resource.Error->{
                    UiEvents.SnackbarEvent(
                        registerState.value?.message ?: "Error!"
                    )
                }
                else -> {
                }
            }
        }
    }
}