package com.sample.factpedia

import app.cash.turbine.test
import com.sample.factpedia.core.data.repository.UserPreferencesRepository
import com.sample.factpedia.core.model.domain.ThemePreference
import com.sample.factpedia.core.testing.repository.FakeUserPreferencesRepository
import com.sample.factpedia.core.testing.util.MainDispatcherRule
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class MainActivityViewModelTest {
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var userPreferencesRepository: UserPreferencesRepository

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    @BeforeEach
    fun setup() {
        userPreferencesRepository = FakeUserPreferencesRepository()
        viewModel = MainActivityViewModel(userPreferencesRepository)
    }

    @Test
    fun `WHEN viewModel is initialized THEN load the correct theme`() = runTest {
        userPreferencesRepository.setThemePreference(ThemePreference.DARK)
        viewModel.state.test {
            assertEquals(
                ThemePreference.SYSTEM,
                awaitItem()
            )
            assertEquals(
                ThemePreference.DARK,
                awaitItem()
            )
        }
    }
}