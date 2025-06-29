package com.sample.factpedia.features.search.domain.repository

import com.sample.factpedia.core.common.result.DataError
import com.sample.factpedia.core.common.result.Response
import com.sample.factpedia.core.common.result.handleError
import com.sample.factpedia.core.data.repository.BookmarksRepository
import com.sample.factpedia.core.data.repository.FactsRepository
import com.sample.factpedia.core.model.domain.BookmarkedFact
import com.sample.factpedia.core.model.mapper.asDomainModel
import com.sample.factpedia.core.model.domain.Fact
import com.sample.factpedia.core.model.mapper.asBookmarkedFact
import com.sample.factpedia.features.search.data.repository.SearchDataSource
import com.sample.factpedia.features.search.data.repository.SearchRepository
import com.sample.factpedia.features.search.di.SearchLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class DefaultSearchRepository @Inject constructor(
    @SearchLocalDataSource private val searchLocalDataSource: SearchDataSource,
    private val factsRepository: FactsRepository,
    private val bookmarksRepository: BookmarksRepository,
): SearchRepository {
    override suspend fun search(query: String): Response<List<Fact>, DataError> {
        return handleError {
            searchLocalDataSource.search(query).map { it.asDomainModel() }
        }
    }

    override fun observeFactsWithBookmarks(ids: List<Int>): Flow<List<BookmarkedFact>> {
        return combine(
            factsRepository.getFactsByIds(ids),
            bookmarksRepository.getAllBookmarks()
        ) { facts, bookmarkedIds ->
            facts.map { it.asBookmarkedFact(isBookmarked = it.id in bookmarkedIds) }
        }
    }
}