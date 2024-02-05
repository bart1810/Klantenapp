package android.compose.data.repository.damageForm

import android.compose.data.remote.request.InspectionRequest
import android.compose.util.Resource
import kotlinx.coroutines.flow.Flow

interface DamageFormRepository {
    suspend fun sendDamageForm(token: String, inspectionRequest: InspectionRequest): Flow<Resource<Any>>
}