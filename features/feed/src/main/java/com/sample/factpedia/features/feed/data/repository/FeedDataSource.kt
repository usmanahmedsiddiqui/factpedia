package com.sample.factpedia.features.feed.data.repository

import com.sample.factpedia.core.model.data.FactApiModel

interface FeedDataSource {
    suspend fun getFeeds(limit: Int): List<FactApiModel>
}