package android.compose.util

import androidx.datastore.preferences.core.stringSetPreferencesKey

object Constants {
    const val BASE_URL = "https://22b7-2001-1c01-4705-3a00-75c3-16ed-b7da-5f56.ngrok-free.app"
    const val AUTH_PREFERENCES = "AUTH_PREF"
    val TOKEN = stringSetPreferencesKey("token")
}