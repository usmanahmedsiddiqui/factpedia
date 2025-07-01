package com.sample.factpedia.core.testing.repository

import com.sample.factpedia.core.data.repository.UserPreferencesRepository
import com.sample.factpedia.core.model.domain.ThemePreference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeUserPreferencesRepository: UserPreferencesRepository {
    private val themePreference = MutableStateFlow(ThemePreference.SYSTEM)

    override val themePreferenceFlow: Flow<ThemePreference> = themePreference.asStateFlow()

    override suspend fun setThemePreference(theme: ThemePreference) {
        themePreference.emit(theme)
    }
}