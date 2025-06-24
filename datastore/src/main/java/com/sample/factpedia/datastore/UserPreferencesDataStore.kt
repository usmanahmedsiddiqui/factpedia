package com.sample.factpedia.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.sample.factpedia.datastore.UserPreferencesDataStore.PreferencesKeys.THEME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

enum class ThemePreference { LIGHT, DARK, SYSTEM }

@Singleton
class UserPreferencesDataStore @Inject constructor(
    context: Context
) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")
    private val dataStore = context.dataStore

    private object PreferencesKeys {
        val THEME = stringPreferencesKey("theme_preference")
    }

    val themePreferenceFlow: Flow<ThemePreference> = dataStore.data
        .catch { emit(emptyPreferences()) }
        .map { preferences ->
            when (preferences[THEME]) {
                ThemePreference.LIGHT.name -> ThemePreference.LIGHT
                ThemePreference.DARK.name -> ThemePreference.DARK
                else -> ThemePreference.SYSTEM
            }
        }

    suspend fun setThemePreference(theme: ThemePreference) {
        dataStore.edit { it[THEME] = theme.name }
    }
}