package com.sample.factpedia.features.feed.domain.usecase

import com.sample.factpedia.core.common.result.DataError
import com.sample.factpedia.core.common.result.Response
import com.sample.factpedia.core.domain.model.Fact
import com.sample.factpedia.features.feed.data.repository.FactRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class LoadRemoteFactsUseCase @Inject constructor(
    private val factRepository: FactRepository,
) {
    suspend operator fun invoke(count: Int): Response<List<Fact>, DataError> {
        delay(5000)
        return factRepository.loadRemoteFacts(count)
    }
}