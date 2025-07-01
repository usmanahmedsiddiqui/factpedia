package com.sample.factpedia.core.data.repository

import com.sample.factpedia.core.model.domain.ThemePreference
import com.sample.factpedia.datastore.UserPreferencesDataStore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultUserPreferencesRepository @Inject constructor(
    private val userPreferencesDataStore: UserPreferencesDataStore
) : UserPreferencesRepository {
    override val themePreferenceFlow: Flow<ThemePreference> =
        userPreferencesDataStore.themePreferenceFlow

    override suspend fun setThemePreference(theme: ThemePreference) {
        userPreferencesDataStore.setThemePreference(theme)
    }
}