package android.compose.presentation.viewmodels.account

import android.compose.data.local.AuthPreferences
import android.compose.data.remote.response.AccountResponse
import android.compose.data.repository.account.AccountRepository
import android.compose.util.Resource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

import kotlinx.coroutines.flow.firstOrNull

class AccountViewModel(
    private val accountRepository: AccountRepository,
    private val authPreferences: AuthPreferences
): ViewModel() {

    private val _accountResponse = MutableStateFlow<Resource<AccountResponse>?>(null)
    val accountResponse = _accountResponse.asStateFlow()

    fun fetAccount() {
        viewModelScope.launch {
            val token = authPreferences.getTokenFlow().firstOrNull()
            if (!token.isNullOrBlank()) {
                accountRepository.getAccount("$token").collectLatest { result ->
                    _accountResponse.value = result
                }
            } else {
                _accountResponse.value = Resource.Error("Authorization token is missing")
            }
        }
    }
}


