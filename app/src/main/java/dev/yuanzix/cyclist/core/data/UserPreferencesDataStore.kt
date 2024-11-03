package dev.yuanzix.cyclist.core.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferencesDataStore(private val context: Context) {
    private object PreferencesKeys {
        val EMAIL = stringPreferencesKey("user_email")
        val JWT_TOKEN = stringPreferencesKey("jwt_token")
    }

    private val Context.dataStore by preferencesDataStore(name = "user_preferences")

    suspend fun saveUserDetails(email: String, jwtToken: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.EMAIL] = email
            preferences[PreferencesKeys.JWT_TOKEN] = jwtToken
        }
    }

    val userEmail: Flow<String?> =
        context.dataStore.data.map { preferences -> preferences[PreferencesKeys.EMAIL] }

    val jwtToken: Flow<String?> =
        context.dataStore.data.map { preferences -> preferences[PreferencesKeys.JWT_TOKEN] }

    suspend fun clearUserDetails() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}