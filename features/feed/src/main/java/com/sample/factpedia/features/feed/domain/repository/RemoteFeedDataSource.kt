package com.sample.factpedia.features.feed.domain.repository

import com.sample.factpedia.core.model.data.FactApiModel
import com.sample.factpedia.features.feed.data.api.FeedApi
import com.sample.factpedia.features.feed.data.repository.FeedDataSource
import javax.inject.Inject

class RemoteFeedDataSource @Inject constructor(
    private val feedApi: FeedApi,
): FeedDataSource {
    override suspend fun getFeeds(limit: Int): List<FactApiModel> {
        return feedApi.getFeeds(limit)
    }
}