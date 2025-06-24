package com.sample.factpedia.features.feed.domain.repository

import com.sample.factpedia.core.model.data.FactApiModel
import com.sample.factpedia.features.feed.data.api.FactsApi
import com.sample.factpedia.features.feed.data.repository.FactDataSource
import javax.inject.Inject

class RemoteFactDataSource @Inject constructor(
    private val factsApi: FactsApi,
): FactDataSource {
    override suspend fun getFacts(limit: Int): List<FactApiModel> {
        return factsApi.getFacts(limit)
    }
}