package android.compose.presentation.viewmodels.auth

import android.compose.common.UiEvents
import android.compose.data.remote.request.ForgotPasswordRequest
import android.compose.data.remote.request.LoginRequest
import android.compose.data.remote.request.RegisterRequest
import android.compose.data.remote.response.LoginResponse
import android.compose.data.repository.auth.AuthRepository
import android.compose.presentation.viewmodels.states.TextFieldState
import android.compose.util.Resource
import android.util.Log
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

@HiltViewModel
class PasswordViewModel@Inject constructor(
    private val authRepository: AuthRepository,
): ViewModel() {

    private val _forgotPasswordState = MutableStateFlow<Resource<Any>?>(null)
    val forgotPasswordState = _forgotPasswordState.asStateFlow()

    private val _emailState = mutableStateOf(TextFieldState())
    val emailState: State<TextFieldState> = _emailState

    private val _newPasswordState = mutableStateOf(TextFieldState())
    val newPasswordState: State<TextFieldState> = _newPasswordState

    private val _confirmPasswordState = mutableStateOf(TextFieldState())
    val confirmPasswordState: State<TextFieldState> = _confirmPasswordState

    private val _keyState = mutableStateOf(TextFieldState())
    val keyState: State<TextFieldState> = _keyState

    private val  _eventFlow = MutableSharedFlow<UiEvents>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun setEmail(value:String){
        _emailState.value = emailState.value.copy(text = value)
    }

    fun setPassword(value:String){
        _newPasswordState.value = newPasswordState.value.copy(text = value)
    }

    fun setConfirmPassword(value:String){
        _confirmPasswordState.value = confirmPasswordState.value.copy(text = value)
    }

    fun setKey(value:String){
        _keyState.value = keyState.value.copy(text = value)
    }

    fun changePasswordInit() {
        viewModelScope.launch {

            authRepository.changePasswordInit(emailState.value.text.toRequestBody()).collectLatest { result ->
                _forgotPasswordState.value = result
            }

            when (forgotPasswordState.value) {
                is Resource.Success -> {
                    _eventFlow.emit(
                        UiEvents.SnackbarEvent(
                            forgotPasswordState.value?.message ?: "Email is verzonden"
                        )
                    )
                }

                is Resource.Error -> {
                    val message = "Er is iets missgegaan met het versturen"

                    _eventFlow.emit(UiEvents.SnackbarEvent(message))
                }

                else -> {
                }
            }
        }
    }

    fun changePasswordFinish() {
        viewModelScope.launch {

            val request = ForgotPasswordRequest (
                key = keyState.value.text,
                newPassword = newPasswordState.value.text,
            )

            authRepository.changePasswordFinish(request).collectLatest { result ->
                _forgotPasswordState.value = result
            }

            when (forgotPasswordState.value) {
                is Resource.Success -> {
                    _eventFlow.emit(
                        UiEvents.NavigateEvent(
                            forgotPasswordState.value?.message ?: "Wachtwoord is gewijzigd"
                        )
                    )
                }

                is Resource.Error -> {
                    val message = "Er is iets missgegaan met het versturen"

                    _eventFlow.emit(UiEvents.SnackbarEvent(message))
                }

                else -> {
                }
            }
        }
    }
}