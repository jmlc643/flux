package com.jluyo.apps.flux.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferencesRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        val KEY_USER_NAME = stringPreferencesKey("user_name")
        val KEY_IS_SETUP_COMPLETE = booleanPreferencesKey("is_setup_complete")
    }

    val userName: Flow<String?> = dataStore.data.map { prefs ->
        prefs[KEY_USER_NAME]
    }

    val isSetupComplete: Flow<Boolean> = dataStore.data.map { prefs ->
        prefs[KEY_IS_SETUP_COMPLETE] ?: false
    }

    suspend fun setUserName(name: String) {
        dataStore.edit { prefs ->
            prefs[KEY_USER_NAME] = name
        }
    }

    suspend fun setSetupComplete(complete: Boolean) {
        dataStore.edit { prefs ->
            prefs[KEY_IS_SETUP_COMPLETE] = complete
        }
    }

    suspend fun clearAll() {
        dataStore.edit { it.clear() }
    }
}
