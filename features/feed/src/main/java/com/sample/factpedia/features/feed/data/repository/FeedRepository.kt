package com.sample.factpedia.features.feed.data.repository

import com.sample.factpedia.core.common.result.DataError
import com.sample.factpedia.core.common.result.Response
import com.sample.factpedia.core.model.domain.Fact

interface FeedRepository {
    suspend fun loadRemoteFacts(limit: Int): Response<List<Fact>, DataError>
}