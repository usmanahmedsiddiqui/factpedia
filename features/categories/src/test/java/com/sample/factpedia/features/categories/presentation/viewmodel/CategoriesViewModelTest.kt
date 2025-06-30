package com.sample.factpedia.features.categories.presentation.viewmodel

import app.cash.turbine.test
import com.sample.factpedia.core.common.result.DataError
import com.sample.factpedia.core.testing.util.MainDispatcherRule
import com.sample.factpedia.features.categories.domain.model.Category
import com.sample.factpedia.features.categories.domain.repository.FakeCategoryRepository
import com.sample.factpedia.features.categories.domain.usecase.SyncCategoriesUseCase
import com.sample.factpedia.features.categories.presentation.actions.CategoryScreenAction
import com.sample.factpedia.features.categories.presentation.state.CategoryScreenState
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class CategoriesViewModelTest {

    private lateinit var viewModel: CategoriesViewModel
    private val categoryRepository = FakeCategoryRepository()
    private val syncCategoriesUseCase = SyncCategoriesUseCase(categoryRepository)

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    @BeforeEach
    fun setup() {
        viewModel = CategoriesViewModel(
            categoryRepository = categoryRepository,
            syncCategoriesUseCase = syncCategoriesUseCase,
        )
    }

    @Test
    fun `GIVEN empty local database and successful remote, WHEN screen is loaded, THEN emits loading and then categories`() = runTest {
        val remoteCategories = listOf(Category(1, "Science"))
        categoryRepository.setRemoteCategories(remoteCategories)

        viewModel.state.test {
            assertEquals(
                CategoryScreenState(
                    isLoading = false,
                    categories = emptyList(),
                    error = null,
                ),
                awaitItem()
            )

            assertEquals(
                CategoryScreenState(
                    isLoading = true,
                    categories = emptyList(),
                    error = null,
                ),
                awaitItem()
            )

            assertEquals(
                CategoryScreenState(
                    isLoading = false,
                    categories = remoteCategories,
                    error = null,
                ),
                awaitItem()
            )
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `GIVEN empty local database and remote error, WHEN screen is loaded, THEN emits loading and then error`() = runTest {
        categoryRepository.shouldFail = true
        val dataError = DataError.Network.FORBIDDEN
        categoryRepository.setRemoteError(dataError)

        viewModel.state.test {
            assertEquals(
                CategoryScreenState(
                    isLoading = false,
                    categories = emptyList(),
                    error = null,
                ),
                awaitItem()
            )

            assertEquals(
                CategoryScreenState(
                    isLoading = true,
                    categories = emptyList(),
                    error = null,
                ),
                awaitItem()
            )

            assertEquals(
                CategoryScreenState(
                    isLoading = false,
                    categories = emptyList(),
                    error = dataError,
                ),
                awaitItem()
            )

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `GIVEN local data and remote error, when screen is loaded, then shows local data and error`() = runTest {
        val localCategories = listOf(Category(1, "Math"))
        categoryRepository.upsertCategories(localCategories)
        val dataError = DataError.Network.FORBIDDEN
        categoryRepository.shouldFail = true
        categoryRepository.setRemoteError(dataError)

        viewModel.state.test {
            assertEquals(
                CategoryScreenState(
                    isLoading = false,
                    categories = emptyList(),
                    error = null,
                ),
                awaitItem()
            )

            assertEquals(
                CategoryScreenState(
                    isLoading = true,
                    categories = emptyList(),
                    error = null,
                ),
                awaitItem()
            )

            assertEquals(
                CategoryScreenState(
                    isLoading = true,
                    categories = localCategories,
                    error = null,
                ),
                awaitItem()
            )

            assertEquals(
                CategoryScreenState(
                    isLoading = false,
                    categories = localCategories,
                    error = dataError,
                ),
                awaitItem()
            )

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `GIVEN no local data and remote error, WHEN retry is called, THEN attempts to sync again and show categories from server when successfully loaded`() = runTest {
        val dataError = DataError.Network.FORBIDDEN
        val remoteCategories = listOf(Category(2, "Tech"))

        categoryRepository.shouldFail = true
        categoryRepository.setRemoteError(dataError)

        viewModel.state.test {
            assertEquals(
                CategoryScreenState(
                    isLoading = false,
                    categories = emptyList(),
                    error = null,
                ),
                awaitItem()
            )

            assertEquals(
                CategoryScreenState(
                    isLoading = true,
                    categories = emptyList(),
                    error = null,
                ),
                awaitItem()
            )

            assertEquals(
                CategoryScreenState(
                    isLoading = false,
                    categories = emptyList(),
                    error = dataError,
                ),
                awaitItem()
            )

            categoryRepository.shouldFail = false
            categoryRepository.setRemoteCategories(remoteCategories)

            viewModel.onAction(CategoryScreenAction.RetryClicked)

            assertEquals(
                CategoryScreenState(
                    isLoading = true,
                    categories = emptyList(),
                    error = null,
                ),
                awaitItem()
            )

            assertEquals(
                CategoryScreenState(
                    isLoading = false,
                    categories = remoteCategories,
                    error = null,
                ),
                awaitItem()
            )

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `GIVEN no remote data and no local data, WHEN screen is loaded THEN show empty state`() = runTest {
        viewModel.state.test {
            assertEquals(
                CategoryScreenState(
                    isLoading = false,
                    categories = emptyList(),
                    error = null,
                ),
                awaitItem()
            )

            assertEquals(
                CategoryScreenState(
                    isLoading = true,
                    categories = emptyList(),
                    error = null,
                ),
                awaitItem()
            )

            assertEquals(
                CategoryScreenState(
                    isLoading = false,
                    categories = emptyList(),
                    error = null,
                ),
                awaitItem()
            )
        }
    }
}