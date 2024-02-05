package android.compose.presentation.viewmodels

import android.compose.data.local.AuthPreferences
import android.compose.data.remote.request.InspectionRequest
import android.compose.data.repository.damageForm.DamageFormRepository
import android.compose.util.Resource
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.io.IOException

class DamageFormViewModel(
    private val damageFormRepository: DamageFormRepository,
    private val authPreferences: AuthPreferences
) : ViewModel() {
    private val _toastMessage = Channel<String>()
    val toastMessage = _toastMessage.receiveAsFlow()

    var isLoading = mutableStateOf(false)
        private set

    fun processImageAndSendForm(context: Context, capturedImageUri: Uri?, kmStand: String, selectedOptionText: String) {
        viewModelScope.launch {
            val byteArray = capturedImageUri?.let { uriToByteArray(context, it) }

            if (byteArray != null) {
                val inspectionRequest = InspectionRequest(
                    code = selectedOptionText,
                    completed = "",
                    odometer = kmStand.toIntOrNull() ?: 0,
                    photo = byteArray,
                    photoContentType = "image/jpeg",
                    result = ""
                )
                sendDamageForm(inspectionRequest)
            } else {
                Log.e("DamageFormViewModel", "Failed to convert image to byte array")
            }
        }
    }

    private fun uriToByteArray(context: Context, uri: Uri): ByteArray? {
        return try {
            context.contentResolver.openInputStream(uri)?.buffered()?.use { it.readBytes() }
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    private fun sendDamageForm(inspectionRequest: InspectionRequest) {
        viewModelScope.launch {
            val token = authPreferences.getTokenFlow().firstOrNull()
            if (!token.isNullOrBlank()) {
                damageFormRepository.sendDamageForm("Bearer $token", inspectionRequest).collect { resource ->
                    isLoading.value = true
                    Log.d("DamageFormViewModel", "Resource received: $resource")
                    if (resource is Resource.Success) {
                        _toastMessage.send("Schadeformulier is succesvol verstuurd!") // send success message to be shown as toast
                    }
                }
            } else {
                Log.d("DamageFormViewModel", "Token is null or blank")
            }
        }
    }
}
