package com.sample.factpedia.core.data.repository

import com.sample.factpedia.core.model.domain.ThemePreference
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {
    val themePreferenceFlow: Flow<ThemePreference>
    suspend fun setThemePreference(theme: ThemePreference)
}