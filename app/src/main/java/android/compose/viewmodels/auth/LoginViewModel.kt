package android.compose.viewmodels.auth

import android.compose.Resource
import android.compose.data.auth.UserRepository
import android.compose.models.CarItem
import android.compose.models.LoginRequest
import androidx.lifecycle.ViewModel
import android.compose.models.LoginResponse
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel(
    private val userRepo: UserRepository
): ViewModel() {

    private val _loginResult = MutableLiveData<LoginResponse>()
    val loginResult = _loginResult

    private val _showErrorToastChannel = Channel<Boolean>()
    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()

    fun loginUser(username: String, pwd: String, rememberMe: Boolean) {

        viewModelScope.launch {
            val loginRequest = LoginRequest(
                password = pwd,
                username = username,
                rememberMe = rememberMe
            )
            userRepo.loginUser(loginRequest).collectLatest { result ->
                when(result) {
                    is Resource.Error -> {
                        _showErrorToastChannel.send(true)
                    }
                    is Resource.Success -> {
                        result.data?.let { login ->
                            _loginResult.value = login
                        }
                    }
                    is Resource.Loading -> TODO()
                }
            }
        }
    }
}