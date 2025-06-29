package com.sample.factpedia.features.bookmarks.presentation.viewmodel

import app.cash.turbine.test
import com.sample.factpedia.core.model.domain.BookmarkedFact
import com.sample.factpedia.core.model.domain.Fact
import com.sample.factpedia.core.testing.repository.FakeBookmarksRepository
import com.sample.factpedia.core.testing.repository.FakeFactRepository
import com.sample.factpedia.core.testing.util.MainDispatcherRule
import com.sample.factpedia.features.bookmarks.domain.repository.DefaultBookmarkedFactsRepository
import com.sample.factpedia.features.bookmarks.presentation.action.BookmarkScreenAction
import com.sample.factpedia.features.bookmarks.presentation.state.BookmarkScreenState
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class BookmarkViewModelTest {
    private lateinit var viewModel: BookmarkViewModel

    private val fakeFactsRepository = FakeFactRepository()
    private val fakeBookmarksRepository = FakeBookmarksRepository()
    private val repository = DefaultBookmarkedFactsRepository(
        factsRepository = fakeFactsRepository,
        bookmarksRepository = fakeBookmarksRepository
    )

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    @BeforeEach
    fun setup() {
        viewModel = BookmarkViewModel(repository)
    }

    @Test
    fun `GIVEN no bookmarks WHEN the screen is loaded THEN empty state is shown`() = runTest {
        viewModel.state.test {
            assertEquals(
                BookmarkScreenState(
                    facts = emptyList()
                ),
                awaitItem()
            )
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `GIVEN bookmarks available WHEN the screen is loaded THEN show bookmarked facts`() =
        runTest {
            val facts = listOf(
                Fact(
                    id = 1,
                    fact = "Fact 1",
                    categoryName = "Category 1",
                    categoryId = 1
                ),
                Fact(
                    id = 2,
                    fact = "Fact 2",
                    categoryName = "Category 2",
                    categoryId = 2
                ),
                Fact(
                    id = 3,
                    fact = "Fact 2",
                    categoryName = "Category 2",
                    categoryId = 2
                ),
            )

            val bookmarkedFactIds = listOf(1, 2)
            fakeFactsRepository.upsertFacts(facts)
            bookmarkedFactIds.forEach { factId ->
                fakeBookmarksRepository.addBookmark(factId)
            }

            val result = listOf(
                BookmarkedFact(
                    id = 1,
                    fact = "Fact 1",
                    categoryName = "Category 1",
                    categoryId = 1,
                    isBookmarked = true
                ),
                BookmarkedFact(
                    id = 2,
                    fact = "Fact 2",
                    categoryName = "Category 2",
                    categoryId = 2,
                    isBookmarked = true
                )
            )

            viewModel.state.test {
                assertEquals(
                    BookmarkScreenState(
                        facts = emptyList()
                    ),
                    awaitItem()
                )


                assertEquals(
                    BookmarkScreenState(
                        facts = result
                    ),
                    awaitItem()
                )

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `GIVEN bookmarks available WHEN a bookmark is removed THEN update the state correctly`() =
        runTest {
            val facts = listOf(
                Fact(
                    id = 1,
                    fact = "Fact 1",
                    categoryName = "Category 1",
                    categoryId = 1
                ),
            )

            val bookmarkedFactIds = listOf(1)
            fakeFactsRepository.upsertFacts(facts)
            bookmarkedFactIds.forEach { factId ->
                fakeBookmarksRepository.addBookmark(factId)
            }

            val result = listOf(
                BookmarkedFact(
                    id = 1,
                    fact = "Fact 1",
                    categoryName = "Category 1",
                    categoryId = 1,
                    isBookmarked = true
                ),
            )

            viewModel.state.test {
                assertEquals(
                    BookmarkScreenState(
                        facts = emptyList()
                    ),
                    awaitItem()
                )


                assertEquals(
                    BookmarkScreenState(
                        facts = result
                    ),
                    awaitItem()
                )

                viewModel.onAction(BookmarkScreenAction.RemoveBookmark(1))

                assertEquals(
                    BookmarkScreenState(
                        facts = emptyList()
                    ),
                    awaitItem()
                )

                cancelAndIgnoreRemainingEvents()
            }
        }
}