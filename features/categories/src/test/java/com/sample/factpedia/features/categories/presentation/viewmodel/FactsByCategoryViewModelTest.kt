package com.sample.factpedia.features.categories.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.sample.factpedia.core.common.result.DataError
import com.sample.factpedia.core.domain.ToggleBookmarkUseCase
import com.sample.factpedia.core.model.domain.BookmarkedFact
import com.sample.factpedia.core.model.domain.Fact
import com.sample.factpedia.core.testing.repository.FakeBookmarksRepository
import com.sample.factpedia.core.testing.repository.FakeFactRepository
import com.sample.factpedia.core.testing.util.MainDispatcherRule
import com.sample.factpedia.features.categories.domain.repository.FakeFactsByCategoryRepository
import com.sample.factpedia.features.categories.domain.usecase.GetFactsByCategoryUseCase
import com.sample.factpedia.features.categories.domain.usecase.SyncFactsByCategoriesUseCase
import com.sample.factpedia.features.categories.presentation.actions.FactsByCategoryScreenAction
import com.sample.factpedia.features.categories.presentation.state.FactsByCategoryScreenState
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class FactsByCategoryViewModelTest {

    private lateinit var factsByCategoryRepository: FakeFactsByCategoryRepository

    private lateinit var factRepository: FakeFactRepository
    private lateinit var bookmarksRepository: FakeBookmarksRepository

    private lateinit var getFactsByCategoryUseCase: GetFactsByCategoryUseCase
    private lateinit var syncFactsByCategoriesUseCase: SyncFactsByCategoriesUseCase
    private lateinit var toggleBookmarkUseCase: ToggleBookmarkUseCase

    private val categoryId = 1
    private val savedStateHandle = SavedStateHandle(mapOf("categoryId" to categoryId))
    private lateinit var viewModel: FactsByCategoryViewModel

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    @BeforeEach
    fun setup() {
        factsByCategoryRepository = FakeFactsByCategoryRepository()
        factRepository = FakeFactRepository()
        bookmarksRepository = FakeBookmarksRepository()
        getFactsByCategoryUseCase = GetFactsByCategoryUseCase(
            factsRepository = factRepository,
            bookmarksRepository = bookmarksRepository,
        )
        syncFactsByCategoriesUseCase = SyncFactsByCategoriesUseCase(
            factsRepository = factRepository
        )
        toggleBookmarkUseCase = ToggleBookmarkUseCase(
            bookmarksRepository = bookmarksRepository,
        )
        viewModel = FactsByCategoryViewModel(
            savedStateHandle = savedStateHandle,
            factsByCategoryRepository = factsByCategoryRepository,
            getFactsByCategoryUseCase = getFactsByCategoryUseCase,
            syncFactsByCategoriesUseCase = syncFactsByCategoriesUseCase,
            toggleBookmarkUseCase = toggleBookmarkUseCase,
        )
    }

    @Test
    fun `GIVEN empty local database and successful remote, WHEN screen is loaded, THEN emits loading and then facts`() =
        runTest {
            val remoteFacts = listOf(Fact(1, "Fact 1", categoryId, "Category 1"))
            factsByCategoryRepository.setRemoteFacts(remoteFacts)

            val bookmarkedFacts =
                listOf(BookmarkedFact(1, "Fact 1", categoryId, "Category 1", isBookmarked = false))

            viewModel.state.test {
                testInitialState(awaitItem())
                testLoadingState(awaitItem())
                testDataState(awaitItem(), bookmarkedFacts)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `GIVEN empty local database and remote error, WHEN screen is loaded, THEN emits loading and then error`() =
        runTest {
            val dataError = DataError.Network.FORBIDDEN
            factsByCategoryRepository.setRemoteError(dataError)

            viewModel.state.test {
                testInitialState(awaitItem())
                testLoadingState(awaitItem())
                testErrorState(awaitItem(), dataError)

                val finalState = awaitItem()
                assertEquals(false, finalState.isLoading)
                assertEquals(emptyList(), finalState.facts)
                assertEquals(finalState.error, dataError)

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `GIVEN empty local database and remote error, WHEN retry is called, THEN attempts to sync again and show categories from server when successfully loaded`() =
        runTest {
            val dataError = DataError.Network.FORBIDDEN
            val remoteFacts = listOf(Fact(1, "Fact 1", categoryId, "Category 1"))

            val bookmarkedFacts =
                listOf(BookmarkedFact(1, "Fact 1", categoryId, "Category 1", isBookmarked = false))

            factsByCategoryRepository.setRemoteError(dataError)

            viewModel.state.test {
                testInitialState(awaitItem())
                testLoadingState(awaitItem())
                testErrorState(awaitItem(), dataError)

                val finalStateBeforeRetry = awaitItem()
                assertEquals(false, finalStateBeforeRetry.isLoading)
                assertEquals(emptyList(), finalStateBeforeRetry.facts)
                assertEquals(dataError, finalStateBeforeRetry.error)

                factsByCategoryRepository.setRemoteFacts(remoteFacts)

                viewModel.onAction(FactsByCategoryScreenAction.RetryClicked(categoryId))

                testLoadingState(awaitItem())
                testDataState(awaitItem(), bookmarkedFacts)

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `GIVEN local data and remote error, when screen is loaded, then shows local data and error`() =
        runTest {
            val localFacts = listOf(Fact(1, "Fact 1", categoryId, "Category 1"))
            factRepository.upsertFacts(localFacts)
            val bookmarkedFact = listOf(
                BookmarkedFact(
                    id = 1,
                    fact = "Fact 1",
                    categoryId = categoryId,
                    categoryName = "Category 1",
                    isBookmarked = false
                )
            )
            val dataError = DataError.Network.FORBIDDEN
            factsByCategoryRepository.setRemoteError(dataError)

            viewModel.state.test {
                testInitialState(awaitItem())
                testLoadingState(awaitItem())
                testErrorState(awaitItem(), dataError)

                val finalState = awaitItem()
                assertEquals(false, finalState.isLoading)
                assertEquals(bookmarkedFact, finalState.facts)
                assertEquals(dataError, finalState.error)

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `GIVEN local data and remote fetch success, when screen is loaded, then shows remote data`() =
        runTest {
            val localFacts = listOf(
                Fact(1, "Fact 1", categoryId, "Category 1")
            )

            val remoteFacts = listOf(
                Fact(1, "Fact 1", categoryId, "Category 1"),
                Fact(2, "Fact 2", categoryId, "Category 1")
            )

            val remoteBookmarkedFacts = listOf(
                BookmarkedFact(1, "Fact 1", categoryId, "Category 1", isBookmarked = false),
                BookmarkedFact(2, "Fact 2", categoryId, "Category 1", isBookmarked = false)
            )

            factRepository.upsertFacts(localFacts)
            factsByCategoryRepository.setRemoteFacts(remoteFacts)

            viewModel.state.test {
                testInitialState(awaitItem())
                testLoadingState(awaitItem())
                testDataState(awaitItem(), remoteBookmarkedFacts)
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
            }
        }

    @Test
    fun `GIVEN facts shown, WHEN toggle bookmark THEN bookmarks are show correctly`() =
        runTest {
            val remoteFacts = listOf(
                Fact(1, "Fact 1", categoryId, "Category 1"),
                Fact(2, "Fact 2", categoryId, "Category 1")
            )

            val unBookmarkedFacts = listOf(
                BookmarkedFact(1, "Fact 1", categoryId, "Category 1", isBookmarked = false),
                BookmarkedFact(2, "Fact 2", categoryId, "Category 1", isBookmarked = false)
            )
            val bookmarkedFacts = listOf(
                BookmarkedFact(1, "Fact 1", categoryId, "Category 1", isBookmarked = true),
                BookmarkedFact(2, "Fact 2", categoryId, "Category 1", isBookmarked = false)
            )

            factsByCategoryRepository.setRemoteFacts(remoteFacts)

            viewModel.state.test {
                testInitialState(awaitItem())
                testLoadingState(awaitItem())
                testDataState(awaitItem(), unBookmarkedFacts)
                viewModel.onAction(FactsByCategoryScreenAction.ToggleBookmark(1, true))
                testDataState(awaitItem(), bookmarkedFacts)
                viewModel.onAction(FactsByCategoryScreenAction.ToggleBookmark(1, false))
                testDataState(awaitItem(), unBookmarkedFacts)
            }
        }

    private fun testInitialState(initialState: FactsByCategoryScreenState) {
        assertEquals(false, initialState.isLoading)
        assertEquals(null, initialState.error)
        assertEquals(emptyList(), initialState.facts)
    }

    private fun testEmptyState(initialState: FactsByCategoryScreenState) {
        assertEquals(false, initialState.isLoading)
        assertEquals(null, initialState.error)
        assertEquals(emptyList(), initialState.facts)
    }

    private fun testLoadingState(loadingState: FactsByCategoryScreenState) {
        assertEquals(true, loadingState.isLoading)
        assertEquals(null, loadingState.error)
        assertEquals(emptyList(), loadingState.facts)
    }

    private fun testDataState(dataState: FactsByCategoryScreenState, facts: List<BookmarkedFact>) {
        assertEquals(null, dataState.error)
        assertEquals(facts, dataState.facts)
        assertEquals(false, dataState.isLoading)
    }

    private fun testErrorState(errorState: FactsByCategoryScreenState, error: DataError) {
        assertEquals(error, errorState.error)
    }
}