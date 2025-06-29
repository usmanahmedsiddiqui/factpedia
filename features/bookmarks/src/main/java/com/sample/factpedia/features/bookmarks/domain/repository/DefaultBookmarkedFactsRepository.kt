package com.sample.factpedia.features.bookmarks.domain.repository

import com.sample.factpedia.core.data.repository.BookmarksRepository
import com.sample.factpedia.core.data.repository.FactsRepository
import com.sample.factpedia.core.model.domain.BookmarkedFact
import com.sample.factpedia.core.model.mapper.asBookmarkedFact
import com.sample.factpedia.features.bookmarks.data.repository.BookmarkedFactsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class DefaultBookmarkedFactsRepository @Inject constructor(
    private val factsRepository: FactsRepository,
    private val bookmarksRepository: BookmarksRepository,
) : BookmarkedFactsRepository {
    override fun getBookmarkedFacts(): Flow<List<BookmarkedFact>> =
        bookmarksRepository.getAllBookmarks()
            .flatMapLatest { ids ->
                factsRepository.getFactsByIds(ids)
                    .map { facts ->
                        facts.map { it.asBookmarkedFact(true) }
                    }
            }

    override suspend fun removeBookmark(id: Int) = bookmarksRepository.removeBookmark(id)
}