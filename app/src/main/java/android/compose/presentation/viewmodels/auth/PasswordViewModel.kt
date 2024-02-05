package android.compose.presentation.viewmodels.auth

import android.compose.common.UiEvents
import android.compose.data.remote.request.ForgotPasswordRequest
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
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasswordViewModel@Inject constructor(
    private val authRepository: AuthRepository,
): ViewModel() {

    private val _changePasswordState = MutableStateFlow<Resource<Any>?>(null)
    private val changePasswordState = _changePasswordState.asStateFlow()

    private val _keyState = mutableStateOf(TextFieldState())
    val keyState: State<TextFieldState> = _keyState

    private val _passwordState = mutableStateOf(TextFieldState())
    val passwordState: State<TextFieldState> = _passwordState

    fun setKey(value:String){
        _keyState.value = keyState.value.copy(text = value)
    }

    fun setPassword(value:String){
        _passwordState.value = passwordState.value.copy(text = value)
    }

    private val  _eventFlow = MutableSharedFlow<UiEvents>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun loginUser() {
        viewModelScope.launch {
            val forgotPasswordRequest = ForgotPasswordRequest(
                key = keyState.value.text,
                newPassword = passwordState.value.text,
            )
            authRepository.changePasswordFinish(forgotPasswordRequest).collectLatest { result ->
                _changePasswordState.value = result
            }

            when (changePasswordState.value) {
                is Resource.Success -> {
                    _eventFlow.emit(
                        UiEvents.NavigateEvent(
                            changePasswordState.value?.message ?: "Success!"
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