package com.sample.factpedia.features.search.domain.repository

import com.sample.factpedia.core.common.result.DataError
import com.sample.factpedia.core.common.result.Response
import com.sample.factpedia.core.common.result.handleError
import com.sample.factpedia.core.model.mapper.asDomainModel
import com.sample.factpedia.core.model.domain.Fact
import com.sample.factpedia.features.search.data.repository.SearchDataSource
import com.sample.factpedia.features.search.data.repository.SearchRepository
import com.sample.factpedia.features.search.di.SearchLocalDataSource
import javax.inject.Inject

class DefaultSearchRepository @Inject constructor(
    @SearchLocalDataSource private val searchLocalDataSource: SearchDataSource,
): SearchRepository {
    override suspend fun search(query: String): Response<List<Fact>, DataError> {
        return handleError {
            searchLocalDataSource.search(query).map { it.asDomainModel() }
        }
    }
}