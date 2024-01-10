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
        private val ACCOUNT_ID = intPreferencesKey("accountId")
    }

    fun getToken(): String {
        var token = ""
        context.dataStore.data.map { preferences ->
            token = preferences[TOKEN_KEY].toString()
        }
        return token
    }

    fun getTokenFlow(): Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[TOKEN_KEY]
        }

    suspend fun saveAccountId(id: Int) {
        context.dataStore.edit { preferences ->
            preferences[ACCOUNT_ID] = id
        }
    }

    fun getAccountIdFlow(): Flow<Int?> = context.dataStore.data
        .map { preferences ->
            preferences[ACCOUNT_ID]
        }


    suspend fun saveToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    suspend fun deleteToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(TOKEN_KEY)
        }
    }
}