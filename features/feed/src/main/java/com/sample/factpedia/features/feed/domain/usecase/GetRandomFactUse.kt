package com.sample.factpedia.features.feed.domain.usecase

import com.sample.factpedia.features.feed.data.repository.FactRepository
import javax.inject.Inject

class GetRandomFactUse @Inject constructor(
    private val factRepository: FactRepository,
) {
    suspend operator fun invoke(factId: Int?) = factRepository.getRandomFact(factId)
}