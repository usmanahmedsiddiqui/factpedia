package com.sample.factpedia.features.settings.viewmodel

import app.cash.turbine.test
import com.sample.factpedia.core.data.repository.UserPreferencesRepository
import com.sample.factpedia.core.model.domain.ThemePreference
import com.sample.factpedia.core.testing.repository.FakeUserPreferencesRepository
import com.sample.factpedia.features.settings.presentation.viewmodel.SettingsViewModel
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class SettingsViewModelTest {
    private lateinit var viewModel: SettingsViewModel
    private lateinit var userPreferencesRepository: UserPreferencesRepository

    @BeforeEach
    fun setup() {
        userPreferencesRepository = FakeUserPreferencesRepository()
        viewModel = SettingsViewModel(userPreferencesRepository)
    }

    @Test
    fun `WHEN viewModel is initialized THEN load the correct theme`() = runTest {
        viewModel.state.test {
            assertEquals(
                ThemePreference.SYSTEM,
                awaitItem()
            )
        }
    }

    @Test
    fun `WHEN the theme is changed THEN load the correct theme`() = runTest {
        val themePreference = ThemePreference.DARK

        viewModel.state.test {
            assertEquals(
                ThemePreference.SYSTEM,
                awaitItem()
            )

            userPreferencesRepository.setThemePreference(themePreference)

            assertEquals(
                themePreference,
                awaitItem()
            )
        }
    }
}