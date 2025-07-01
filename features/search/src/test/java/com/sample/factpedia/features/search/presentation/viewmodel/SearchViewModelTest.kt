package com.sample.factpedia.features.search.presentation.viewmodel

import app.cash.turbine.test
import com.sample.factpedia.core.common.result.DataError
import com.sample.factpedia.core.domain.ToggleBookmarkUseCase
import com.sample.factpedia.core.model.domain.BookmarkedFact
import com.sample.factpedia.core.model.domain.Fact
import com.sample.factpedia.core.model.mapper.asBookmarkedFact
import com.sample.factpedia.core.testing.repository.FakeBookmarksRepository
import com.sample.factpedia.core.testing.repository.FakeFactRepository
import com.sample.factpedia.core.testing.util.MainDispatcherRule
import com.sample.factpedia.features.search.domain.repository.FakeSearchRepository
import com.sample.factpedia.features.search.domain.usecase.SearchFactsFromLocalDatabaseUseCase
import com.sample.factpedia.features.search.presentation.action.SearchScreenAction
import com.sample.factpedia.features.search.presentation.state.SearchScreenState
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class SearchViewModelTest {
    private lateinit var searchRepository: FakeSearchRepository
    private lateinit var factsRepository: FakeFactRepository
    private lateinit var bookmarksRepository: FakeBookmarksRepository
    private lateinit var searchFactsFromLocalDatabaseUseCase: SearchFactsFromLocalDatabaseUseCase
    private lateinit var toggleBookmarkUseCase: ToggleBookmarkUseCase

    private lateinit var viewModel: SearchViewModel


    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    @BeforeEach
    fun setup() {
        searchRepository = FakeSearchRepository()
        factsRepository = FakeFactRepository()
        bookmarksRepository = FakeBookmarksRepository()
        searchFactsFromLocalDatabaseUseCase = SearchFactsFromLocalDatabaseUseCase(
            factsRepository = factsRepository,
            bookmarksRepository = bookmarksRepository,
        )
        toggleBookmarkUseCase = ToggleBookmarkUseCase(
            bookmarksRepository = bookmarksRepository,
        )

        viewModel = SearchViewModel(
            searchRepository = searchRepository,
            factsRepository = factsRepository,
            searchFactsFromLocalDatabaseUseCase = searchFactsFromLocalDatabaseUseCase,
            toggleBookmarkUseCase = toggleBookmarkUseCase
        )
    }

    @Test
    fun `GIVEN empty local database and remote error, WHEN screen is loaded, THEN show error`() =
        runTest {
            val query = "science"
            val dataError = DataError.Network.FORBIDDEN
            searchRepository.setRemoteError(dataError)
            viewModel.onAction(SearchScreenAction.TriggerSearch(query))
            viewModel.state.test {
                testInitialState(awaitItem())
                testQuerySate(awaitItem(), query)
                testErrorState(awaitItem(), dataError)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `GIVEN empty local database remote success, WHEN screen is loaded, THEN show search content`() =
        runTest {
            val query = "science"
            val remoteFacts = listOf(
                Fact(1, "Science 1", 1, "Science"),
                Fact(2, "Science 2", 1, "Science"),
            )

            val bookmarkedFacts = remoteFacts.map { it.asBookmarkedFact(false) }

            searchRepository.setRemoteFacts(remoteFacts)

            viewModel.onAction(SearchScreenAction.TriggerSearch(query))
            viewModel.state.test {
                testInitialState(awaitItem())
                testQuerySate(awaitItem(), query)
                testDataState(awaitItem(), bookmarkedFacts)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `GIVEN empty local database and remote success, WHEN screen is loaded but search query doesn't match THEN show empty state`() =
        runTest {
            val remoteFacts = listOf(
                Fact(1, "Science 1", 1, "Science"),
                Fact(2, "Science 2", 1, "Science"),
            )

            val bookmarkedFacts = remoteFacts.map { it.asBookmarkedFact(false) }

            searchRepository.setRemoteFacts(remoteFacts)

            viewModel.state.test {
                val query1 = "entertainment"
                viewModel.onAction(SearchScreenAction.TriggerSearch(query1))
                testInitialState(awaitItem())
                testQuerySate(awaitItem(), query1)

                val query2 = "science"
                viewModel.onAction(SearchScreenAction.TriggerSearch(query2))
                testQuerySate(awaitItem(), query2)
                testDataState(awaitItem(), bookmarkedFacts)

                val query3 = "animal"
                viewModel.onAction(SearchScreenAction.TriggerSearch(query3))
                testQuerySate(awaitItem(), query3)
                testDataState(awaitItem(), emptyList())
                cancelAndIgnoreRemainingEvents()
            }
        }


    @Test
    fun `GIVEN local database and remote error, WHEN screen is loaded THEN search from local database`() =
        runTest {
            val dataError = DataError.Network.FORBIDDEN
            searchRepository.setRemoteError(dataError)

            val localFacts = listOf(
                Fact(1, "Science 1", 1, "Science"),
                Fact(2, "Science 2", 1, "Science"),
            )

            val bookmarkedFacts = localFacts.map { it.asBookmarkedFact(false) }

            factsRepository.upsertFacts(localFacts)

            viewModel.state.test {
                val query = "science"
                viewModel.onAction(SearchScreenAction.TriggerSearch(query))
                testInitialState(awaitItem())
                testQuerySate(awaitItem(), query)
                testErrorState(awaitItem(), dataError)
                val finalState = awaitItem()
                assertEquals(dataError, finalState.error)
                assertEquals(bookmarkedFacts, finalState.searchResults)

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `GIVEN local database and remote success, WHEN screen is loaded THEN insert remote response into database and search from local database`() =
        runTest {
            val localFacts = listOf(
                Fact(1, "Science 1", 1, "Science"),
                Fact(2, "Science 2", 1, "Science"),
            )

            val remoteFacts = listOf(
                Fact(3, "Science 3", 1, "Science"),
            )

            searchRepository.setRemoteFacts(remoteFacts)

            val bookmarkedFacts = listOf(
                BookmarkedFact(1, "Science 1", 1, "Science", false),
                BookmarkedFact(2, "Science 2", 1, "Science", false),
                BookmarkedFact(3, "Science 3", 1, "Science", false),
            )

            factsRepository.upsertFacts(localFacts)

            viewModel.state.test {
                val query = "science"
                viewModel.onAction(SearchScreenAction.TriggerSearch(query))
                testInitialState(awaitItem())
                testQuerySate(awaitItem(), query)
                testDataState(awaitItem(), bookmarkedFacts)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `GIVEN facts are loaded on screen, WHEN clear clicked THEN clear the search`() =
        runTest {
            val remoteFacts = listOf(
                Fact(1, "Science 1", 1, "Science"),
            )

            searchRepository.setRemoteFacts(remoteFacts)

            val bookmarkedFacts = listOf(
                BookmarkedFact(1, "Science 1", 1, "Science", false),
            )

            viewModel.state.test {
                val query = "science"
                viewModel.onAction(SearchScreenAction.TriggerSearch(query))
                testInitialState(awaitItem())
                testQuerySate(awaitItem(), query)
                testDataState(awaitItem(), bookmarkedFacts)
                viewModel.onAction(SearchScreenAction.ClearSearch)
                testQuerySate(awaitItem(), "")
                testInitialState(awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `GIVEN facts are loaded on screen, WHEN bookmark toggle THEN update the bookmark`() =
        runTest {
            val localFact = listOf(
                Fact(1, "Science 1", 1, "Science"),
            )

            factsRepository.upsertFacts(localFact)

            val unBookmarkedFacts = listOf(
                BookmarkedFact(1, "Science 1", 1, "Science", false),
            )

            val bookmarkedFacts = listOf(
                BookmarkedFact(1, "Science 1", 1, "Science", true),
            )

            viewModel.state.test {
                val query = "science"
                viewModel.onAction(SearchScreenAction.TriggerSearch(query))
                testInitialState(awaitItem())
                testQuerySate(awaitItem(), query)
                testDataState(awaitItem(), unBookmarkedFacts)
                bookmarksRepository.addBookmark(1)
                testDataState(awaitItem(), bookmarkedFacts)
                bookmarksRepository.removeBookmark(1)
                testDataState(awaitItem(), unBookmarkedFacts)
                cancelAndIgnoreRemainingEvents()
            }
        }


    private fun testInitialState(state: SearchScreenState) {
        assertEquals(null, state.error)
        assertEquals(emptyList(), state.searchResults)
    }

    private fun testQuerySate(state: SearchScreenState, query: String) {
        assertEquals(null, state.error)
        assertEquals(query, state.query)
    }

    private fun testErrorState(state: SearchScreenState, error: DataError) {
        assertEquals(error, state.error)
        assertEquals(emptyList(), state.searchResults)
    }

    private fun testDataState(state: SearchScreenState, data: List<BookmarkedFact>) {
        assertEquals(null, state.error)
        assertEquals(data, state.searchResults)
    }

}