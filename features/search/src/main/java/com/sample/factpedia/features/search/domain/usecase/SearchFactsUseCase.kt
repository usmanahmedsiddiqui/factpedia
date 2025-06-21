package com.sample.factpedia.features.search.domain.usecase

import com.sample.factpedia.core.common.result.DataError
import com.sample.factpedia.core.common.result.Response
import com.sample.factpedia.core.domain.model.Fact
import com.sample.factpedia.features.search.data.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchFactsUseCase @Inject constructor(
    private val searchRepository: SearchRepository,
) {
    suspend operator fun invoke(query: String): Flow<Response<List<Fact>, DataError>> {
        return when (val result = searchRepository.search(query)) {
            is Response.Failure -> flowOf(Response.Failure(result.error))
            is Response.Success -> searchRepository.observeFactsWithBookmarks(result.data.map { it.id })
                .map { Response.Success(it) }
        }
    }
}