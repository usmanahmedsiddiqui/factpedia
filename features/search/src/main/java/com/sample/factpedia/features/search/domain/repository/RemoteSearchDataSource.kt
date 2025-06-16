package com.sample.factpedia.features.search.domain.repository

import com.sample.factpedia.core.data.model.FactApiModel
import com.sample.factpedia.features.search.data.api.SearchApi
import com.sample.factpedia.features.search.data.repository.SearchDataSource
import javax.inject.Inject

class RemoteSearchDataSource @Inject constructor(
    private val searchApi: SearchApi,
): SearchDataSource {
    override suspend fun search(query: String): List<FactApiModel> {
        return searchApi.searchFacts(query)
    }
}