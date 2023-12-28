package android.compose.data.local

import android.compose.util.Constants.TOKEN
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit

class AuthPreferences(private val dataStore: DataStore<Preferences>) {
    suspend fun saveAuthToken(loginToken:String){
        dataStore.edit { pref ->
            pref[TOKEN] = setOf(loginToken)
        }
    }
}