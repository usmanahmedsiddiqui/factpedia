package com.sample.factpedia.features.categories.domain.usecase

import com.sample.factpedia.core.data.repository.FactsRepository
import com.sample.factpedia.core.model.domain.Fact
import javax.inject.Inject

class SyncFactsByCategoriesUseCase @Inject constructor(
    private val factsRepository: FactsRepository
) {
    suspend operator fun invoke(categoryId: Int, facts: List<Fact>) {
        factsRepository.upsertFacts(facts)
        val remoteIds = facts.map { it.id }
        factsRepository.deleteFactsNotInCategory(categoryId, remoteIds)
    }
}