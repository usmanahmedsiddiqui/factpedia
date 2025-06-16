package com.sample.factpedia.features.search.data.repository

import com.sample.factpedia.core.common.result.DataError
import com.sample.factpedia.core.common.result.Response
import com.sample.factpedia.core.domain.model.Fact

interface SearchRepository {
    suspend fun search(query: String): Response<List<Fact>, DataError>
}