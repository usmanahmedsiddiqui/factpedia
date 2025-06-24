package com.sample.factpedia.features.feed.domain.repository

import com.sample.factpedia.core.common.result.DataError
import com.sample.factpedia.core.common.result.Response
import com.sample.factpedia.core.common.result.handleError
import com.sample.factpedia.core.model.data.asDomainModel
import com.sample.factpedia.core.model.domain.Fact
import com.sample.factpedia.database.dao.BookmarkDao
import com.sample.factpedia.database.dao.FactDao
import com.sample.factpedia.database.model.asDomainModel
import com.sample.factpedia.features.feed.data.repository.FactDataSource
import com.sample.factpedia.features.feed.data.repository.FactRepository
import com.sample.factpedia.features.feed.di.FactLocalDataSource
import kotlinx.coroutines.delay
import javax.inject.Inject

class DefaultFactRepository @Inject constructor(
    @FactLocalDataSource private val factDataSource: FactDataSource,
    private val factDao: FactDao,
    private val bookmarkDao: BookmarkDao,
) : FactRepository {

    override suspend fun getRandomFact(factId: Int?): Fact? {
        val factEntity = if (factId != null) {
            factDao.getRandomFactExcluding(factId) ?: factDao.getRandomFact()
        } else {
            factDao.getRandomFact()
        }
        factEntity ?: return null

        val isBookmarked = bookmarkDao.isBookmarked(factEntity.id)
        return factEntity.asDomainModel(isBookmarked)
    }

    override suspend fun loadRemoteFacts(limit: Int): Response<List<Fact>, DataError> {
        delay(5000)
        return handleError {
            factDataSource.getFacts(limit).map { it.asDomainModel() }
        }
    }
}