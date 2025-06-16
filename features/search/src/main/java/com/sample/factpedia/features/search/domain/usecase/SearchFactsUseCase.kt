package com.sample.factpedia.features.search.domain.usecase

import com.sample.factpedia.core.common.result.DataError
import com.sample.factpedia.core.common.result.Response
import com.sample.factpedia.core.domain.model.Fact
import com.sample.factpedia.features.search.data.repository.SearchRepository
import javax.inject.Inject

class SearchFactsUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(query: String): Response<List<Fact>, DataError> {
        return searchRepository.search(query)
    }
}