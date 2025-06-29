package com.sample.factpedia.features.feed.domain.repository

import com.sample.factpedia.core.common.result.DataError
import com.sample.factpedia.core.common.result.Response
import com.sample.factpedia.core.common.result.handleError
import com.sample.factpedia.core.data.repository.BookmarksRepository
import com.sample.factpedia.core.data.repository.FactsRepository
import com.sample.factpedia.core.model.domain.BookmarkedFact
import com.sample.factpedia.core.model.mapper.asDomainModel
import com.sample.factpedia.core.model.domain.Fact
import com.sample.factpedia.core.model.mapper.asBookmarkedFact
import com.sample.factpedia.features.feed.data.repository.FeedDataSource
import com.sample.factpedia.features.feed.data.repository.FeedRepository
import com.sample.factpedia.features.feed.di.FeedLocalDataSource
import kotlinx.coroutines.delay
import javax.inject.Inject

class DefaultFeedRepository @Inject constructor(
    @FeedLocalDataSource private val feedDataSource: FeedDataSource,
    private val factsRepository: FactsRepository,
    private val bookmarksRepository: BookmarksRepository,
) : FeedRepository {

    override suspend fun getRandomFact(factId: Int?): BookmarkedFact? {
        val fact = if (factId != null) {
            factsRepository.getRandomFactExcluding(factId) ?: factsRepository.getRandomFact()
        } else {
            factsRepository.getRandomFact()
        }
        fact ?: return null

        val isBookmarked = bookmarksRepository.isBookmarked(fact.id)
        return fact.asBookmarkedFact(isBookmarked)
    }

    override suspend fun loadRemoteFacts(limit: Int): Response<List<Fact>, DataError> {
        delay(5000)
        return handleError {
            feedDataSource.getFeeds(limit).map { it.asDomainModel() }
        }
    }
}