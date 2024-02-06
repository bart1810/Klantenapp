package android.compose.data.local

import android.compose.di.dataStore
import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AuthPreferences(private val context: Context) {
    companion object {
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val PASSWORD = stringPreferencesKey("password")
    }

    fun getToken(): String {
        var token = ""
        context.dataStore.data.map { preferences ->
            token = preferences[TOKEN_KEY].toString()
        }
        return token
    }

    fun getPasswordFlow(): Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[PASSWORD]
        }

    fun getTokenFlow(): Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[TOKEN_KEY]
        }

    suspend fun saveToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    suspend fun savePassword(password: String) {
        context.dataStore.edit { preferences ->
            preferences[PASSWORD] = password
        }
    }

    suspend fun deleteToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(TOKEN_KEY)
        }
    }
}