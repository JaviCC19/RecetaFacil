package com.myproject.JavierCifuentes.Data.DataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

object UserPreferencesKeys {
    val USER_NAME = stringPreferencesKey("user_name")
}

class DataStoreManager(private val context: Context) {

    suspend fun saveUserName(name: String) {
        context.dataStore.edit { preferences ->
            preferences[UserPreferencesKeys.USER_NAME] = name
        }
    }

    fun getUserName(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[UserPreferencesKeys.USER_NAME]
        }
    }

    suspend fun clearUserName() {
        context.dataStore.edit { preferences ->
            preferences.remove(UserPreferencesKeys.USER_NAME)
        }
    }
}