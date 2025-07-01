package com.sample.factpedia.features.feed.domain.repository

import com.sample.factpedia.core.common.result.DataError
import com.sample.factpedia.core.common.result.Response
import com.sample.factpedia.core.common.result.handleError
import com.sample.factpedia.core.model.mapper.asDomainModel
import com.sample.factpedia.core.model.domain.Fact
import com.sample.factpedia.features.feed.data.repository.FeedDataSource
import com.sample.factpedia.features.feed.data.repository.FeedRepository
import com.sample.factpedia.features.feed.di.FeedLocalDataSource
import kotlinx.coroutines.delay
import javax.inject.Inject

class DefaultFeedRepository @Inject constructor(
    @FeedLocalDataSource private val feedDataSource: FeedDataSource,
) : FeedRepository {
    override suspend fun loadRemoteFacts(limit: Int): Response<List<Fact>, DataError> {
        delay(5000)
        return handleError {
            feedDataSource.getFeeds(limit).map { it.asDomainModel() }
        }
    }
}