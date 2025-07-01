package com.sample.factpedia.features.feed.presentation.viewmodel

import app.cash.turbine.test
import com.sample.factpedia.core.common.result.DataError
import com.sample.factpedia.core.domain.ObserveBookmarkStatusUseCase
import com.sample.factpedia.core.domain.ToggleBookmarkUseCase
import com.sample.factpedia.core.model.domain.BookmarkedFact
import com.sample.factpedia.core.model.domain.Fact
import com.sample.factpedia.core.model.mapper.asBookmarkedFact
import com.sample.factpedia.core.testing.repository.FakeBookmarksRepository
import com.sample.factpedia.core.testing.repository.FakeFactRepository
import com.sample.factpedia.core.testing.util.MainDispatcherRule
import com.sample.factpedia.features.feed.domain.repository.FakeFeedRepository
import com.sample.factpedia.features.feed.domain.usecase.GetRandomFactUseCase
import com.sample.factpedia.features.feed.presentation.actions.FeedScreenAction
import com.sample.factpedia.features.feed.presentation.state.FeedScreenState
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class FeedScreenViewModelTest {
    private lateinit var factsRepository: FakeFactRepository
    private lateinit var bookmarksRepository: FakeBookmarksRepository
    private lateinit var feedRepository: FakeFeedRepository
    private lateinit var toggleBookmarkUseCase: ToggleBookmarkUseCase
    private lateinit var observeBookmarkStatusUseCase: ObserveBookmarkStatusUseCase
    private lateinit var getRandomFactUseCase: GetRandomFactUseCase
    private lateinit var viewModel: FeedScreenViewModel

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    @BeforeEach
    fun setup() {
        factsRepository = FakeFactRepository()
        bookmarksRepository = FakeBookmarksRepository()
        feedRepository = FakeFeedRepository()
        toggleBookmarkUseCase = ToggleBookmarkUseCase(bookmarksRepository)
        observeBookmarkStatusUseCase = ObserveBookmarkStatusUseCase(bookmarksRepository)
        getRandomFactUseCase = GetRandomFactUseCase(factsRepository, bookmarksRepository)
        viewModel = FeedScreenViewModel(
            feedRepository = feedRepository,
            factsRepository = factsRepository,
            toggleBookmarkUseCase = toggleBookmarkUseCase,
            observeBookmarkStatusUseCase = observeBookmarkStatusUseCase,
            getRandomFactUseCase = getRandomFactUseCase
        )
    }

    @Test
    fun `GIVEN empty local facts and remote loading success WHEN the screen is loaded THEN show loading and fact`() =
        runTest {
            val facts = listOf(
                Fact(1, "Fact 1", 1, "Category 1"),
            )
            feedRepository.setRemoteFacts(facts)
            val bookmarkedFact = BookmarkedFact(1, "Fact 1", 1, "Category 1", isBookmarked = false)
            viewModel.state.test {
                testInitialState(awaitItem())
                testLoadingState(awaitItem())
                testDataState(awaitItem(), bookmarkedFact)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `GIVEN empty local database and remote error WHEN the screen is loaded THEN show loading and error`() =
        runTest {
            val dataError = DataError.Network.FORBIDDEN
            feedRepository.setRemoteError(dataError)
            viewModel.state.test {
                testInitialState(awaitItem())
                testLoadingState(awaitItem())
                testErrorState(awaitItem(), dataError)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `GIVEN empty local database and remote error, WHEN retry is called, THEN attempts to load again and show fact from server when successfully loaded`() =
        runTest {
            val dataError = DataError.Network.FORBIDDEN
            val facts = listOf(
                Fact(1, "Fact 1", 1, "Category 1"),
            )
            val bookmarkedFact = BookmarkedFact(1, "Fact 1", 1, "Category 1", isBookmarked = false)
            feedRepository.setRemoteError(dataError)
            viewModel.state.test {
                testInitialState(awaitItem())
                testLoadingState(awaitItem())
                testErrorState(awaitItem(), dataError)

                feedRepository.setRemoteFacts(facts)
                viewModel.onAction(FeedScreenAction.Retry)
                testLoadingState(awaitItem())
                testDataState(awaitItem(), bookmarkedFact)

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `GIVEN empty local database and remote success with no data, WHEN ths screen is loaded THEN show empty state`() =
        runTest {
            viewModel.state.test {
                testInitialState(awaitItem())
                testLoadingState(awaitItem())
                testEmptyState(awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `GIVEN local data available, WHEN screen is loaded THEN show fact from local database`() =
        runTest {
            val facts = listOf(
                Fact(1, "Fact 1", 1, "Category 1"),
            )
            factsRepository.upsertFacts(facts)

            viewModel.state.test {
                testInitialState(awaitItem())
                testDataState(awaitItem(), facts[0].asBookmarkedFact(false))
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `GIVEN local data available, WHEN refresh is clicked THEN show next fact`() =
        runTest {
            val localFacts = listOf(
                Fact(1, "Fact 1", 1, "Category 1"),
                Fact(2, "Fact 2", 2, "Category 2"),
            )

            factsRepository.upsertFacts(localFacts)

            viewModel.state.test {
                testInitialState(awaitItem())
                val firstFact = awaitItem()
                assertEquals(localFacts[0].asBookmarkedFact(false), firstFact.fact)
                assertEquals(0, firstFact.refreshCount)

                viewModel.onAction(FeedScreenAction.Refresh)
                testRefreshCountUpdate(awaitItem(), 1)

                 val secondFact = awaitItem()
                 assertEquals(localFacts[1].asBookmarkedFact(false), secondFact.fact)

                 cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `GIVEN feed shown, WHEN toggle bookmark THEN update the state correctly`() =
        runTest {
            val facts = listOf(
                Fact(1, "Fact 1", 1, "Category 1"),
            )
            factsRepository.upsertFacts(facts)

            viewModel.state.test {
                testInitialState(awaitItem())
                testDataState(awaitItem(), facts[0].asBookmarkedFact(false))
                toggleBookmarkUseCase(1,true)
                testDataState(awaitItem(), facts[0].asBookmarkedFact(true))
                toggleBookmarkUseCase(1,false)
                testDataState(awaitItem(), facts[0].asBookmarkedFact(false))

                cancelAndIgnoreRemainingEvents()
            }
        }



    private fun testInitialState(state: FeedScreenState) {
        assertEquals(false, state.isLoading)
        assertEquals(null, state.error)
        assertEquals(0, state.refreshCount)
        assertEquals(null, state.fact)
    }

    private fun testLoadingState(state: FeedScreenState) {
        assertEquals(true, state.isLoading)
        assertEquals(null, state.error)
        assertEquals(0, state.refreshCount)
        assertEquals(null, state.fact)
    }


    private fun testEmptyState(state: FeedScreenState) {
        assertEquals(false, state.isLoading)
        assertEquals(null, state.error)
        assertEquals(0, state.refreshCount)
        assertEquals(null, state.fact)
    }

    private fun testRefreshCountUpdate(state: FeedScreenState, refreshCount: Int) {
        assertEquals(refreshCount, state.refreshCount)
    }

    private fun testDataState(state: FeedScreenState, fact: BookmarkedFact) {
        assertEquals(false, state.isLoading)
        assertEquals(null, state.error)
        assertEquals(0, state.refreshCount)
        assertEquals(fact, state.fact)
    }

    private fun testErrorState(state: FeedScreenState, error: DataError) {
        assertEquals(false, state.isLoading)
        assertEquals(error, state.error)
        assertEquals(0, state.refreshCount)
        assertEquals(null, state.fact)
    }


}