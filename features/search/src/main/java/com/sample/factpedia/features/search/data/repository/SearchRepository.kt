package com.sample.factpedia.features.search.data.repository

import com.sample.factpedia.core.common.result.DataError
import com.sample.factpedia.core.common.result.Response
import com.sample.factpedia.core.model.domain.Fact
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun search(query: String): Response<List<Fact>, DataError>
    fun observeFactsWithBookmarks(ids: List<Int>): Flow<List<Fact>>
}