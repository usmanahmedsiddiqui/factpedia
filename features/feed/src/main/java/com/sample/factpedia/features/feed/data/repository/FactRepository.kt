package com.sample.factpedia.features.feed.data.repository

import com.sample.factpedia.core.common.result.DataError
import com.sample.factpedia.core.common.result.Response
import com.sample.factpedia.core.model.domain.Fact

interface FactRepository {
    suspend fun getRandomFact(factId: Int?): Fact?
    suspend fun loadRemoteFacts(limit: Int): Response<List<Fact>, DataError>
}