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
    private lateinit var categoryRepository: FakeCategoryRepository
    private lateinit var syncCategoriesUseCase: SyncCategoriesUseCase

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    @BeforeEach
    fun setup() {
        categoryRepository = FakeCategoryRepository()
        syncCategoriesUseCase = SyncCategoriesUseCase(categoryRepository)
        viewModel = CategoriesViewModel(
            categoryRepository = categoryRepository,
            syncCategoriesUseCase = syncCategoriesUseCase,
        )
    }

    @Test
    fun `GIVEN empty local database and remote loading success, WHEN screen is loaded, THEN emits loading and then categories`() =
        runTest {
            val remoteCategories = listOf(Category(1, "Science"))
            categoryRepository.setRemoteCategories(remoteCategories)

            viewModel.state.test {
                testInitialState(awaitItem())
                testLoadingState(awaitItem())
                testDataState(awaitItem(), remoteCategories)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `GIVEN empty local database and remote error, WHEN screen is loaded, THEN emits loading and then error`() =
        runTest {
            val dataError = DataError.Network.FORBIDDEN
            categoryRepository.setRemoteError(dataError)


            viewModel.state.test {
                testInitialState(awaitItem())
                testLoadingState(awaitItem())
                testErrorState(awaitItem(), dataError)

                val finalState = awaitItem()
                assertEquals(false, finalState.isLoading)
                assertEquals(emptyList(), finalState.categories)
                assertEquals(finalState.error, dataError)

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `GIVEN empty local database and remote error, WHEN retry is called, THEN attempts to sync again and show categories from server when successfully loaded`() =
        runTest {
            val dataError = DataError.Network.FORBIDDEN
            val remoteCategories = listOf(Category(2, "Tech"))

            categoryRepository.setRemoteError(dataError)

            viewModel.state.test {
                testInitialState(awaitItem())
                testLoadingState(awaitItem())
                testErrorState(awaitItem(), dataError)
                val finalStateBeforeRetry = awaitItem()
                assertEquals(false, finalStateBeforeRetry.isLoading)
                assertEquals(emptyList(), finalStateBeforeRetry.categories)
                assertEquals(dataError, finalStateBeforeRetry.error)

                categoryRepository.setRemoteCategories(remoteCategories)

                viewModel.onAction(CategoryScreenAction.RetryClicked)

                testLoadingState(awaitItem())
                testDataState(awaitItem(), remoteCategories)

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `GIVEN local data and remote error, when screen is loaded, then shows local data and error`() =
        runTest {
            val localCategories = listOf(Category(1, "Math"))
            categoryRepository.upsertCategories(localCategories)
            val dataError = DataError.Network.FORBIDDEN
            categoryRepository.setRemoteError(dataError)

            viewModel.state.test {
                testInitialState(awaitItem())
                testLoadingState(awaitItem())
                testErrorState(awaitItem(), dataError)

                val finalState = awaitItem()
                assertEquals(false, finalState.isLoading)
                assertEquals(localCategories, finalState.categories)
                assertEquals(dataError, finalState.error)

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `GIVEN local data and remote fetch success, when screen is loaded, then shows remote data`() =
        runTest {
            val localCategories = listOf(Category(1, "Math"))
            val remoteCategories = listOf(Category(1, "Math"), Category(2, "Physics"))
            categoryRepository.upsertCategories(localCategories)
            categoryRepository.setRemoteCategories(remoteCategories)

            viewModel.state.test {
                testInitialState(awaitItem())
                testLoadingState(awaitItem())
                testDataState(awaitItem(), remoteCategories)
                cancelAndIgnoreRemainingEvents()
            }
        }


    @Test
    fun `GIVEN no remote data and no local data, WHEN screen is loaded THEN show empty state`() =
        runTest {
            viewModel.state.test {
                testInitialState(awaitItem())
                testLoadingState(awaitItem())
                testEmptyState(awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }

    private fun testInitialState(state: CategoryScreenState) {
        assertEquals(false, state.isLoading)
        assertEquals(null, state.error)
        assertEquals(emptyList(), state.categories)
    }

    private fun testEmptyState(state: CategoryScreenState) {
        assertEquals(false, state.isLoading)
        assertEquals(null, state.error)
        assertEquals(emptyList(), state.categories)
    }

    private fun testLoadingState(state: CategoryScreenState) {
        assertEquals(true, state.isLoading)
        assertEquals(null, state.error)
        assertEquals(emptyList(), state.categories)
    }

    private fun testDataState(state: CategoryScreenState, categories: List<Category>) {
        assertEquals(null, state.error)
        assertEquals(categories, state.categories)
        assertEquals(false, state.isLoading)
    }

    private fun testErrorState(errorState: CategoryScreenState, error: DataError) {
        assertEquals(error, errorState.error)
    }
}