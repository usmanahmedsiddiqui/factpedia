package com.sample.factpedia.features.search.domain.repository

import com.sample.factpedia.core.common.result.DataError
import com.sample.factpedia.core.common.result.Response
import com.sample.factpedia.core.common.result.handleError
import com.sample.factpedia.core.model.data.asDomainModel
import com.sample.factpedia.core.model.domain.Fact
import com.sample.factpedia.database.dao.BookmarkDao
import com.sample.factpedia.database.dao.FactDao
import com.sample.factpedia.database.model.asDomainModel
import com.sample.factpedia.features.search.data.repository.SearchDataSource
import com.sample.factpedia.features.search.data.repository.SearchRepository
import com.sample.factpedia.features.search.di.SearchLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class DefaultSearchRepository @Inject constructor(
    @SearchLocalDataSource private val searchLocalDataSource: SearchDataSource,
    private val factDao: FactDao,
    private val bookmarkDao: BookmarkDao,
): SearchRepository {
    override suspend fun search(query: String): Response<List<Fact>, DataError> {
        return handleError {
            searchLocalDataSource.search(query).map { it.asDomainModel() }
        }
    }

    override fun observeFactsWithBookmarks(ids: List<Int>): Flow<List<Fact>> {
        return combine(
            factDao.getFactsByIds(ids),
            bookmarkDao.getAllBookmarks()
        ) { facts, bookmarks ->
            val bookmarkedIds = bookmarks.map { it.factId }.toSet()
            facts.map { it.asDomainModel(isBookmarked = it.id in bookmarkedIds) }
        }
    }
}