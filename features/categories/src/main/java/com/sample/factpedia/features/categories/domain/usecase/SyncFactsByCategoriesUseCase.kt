package com.sample.factpedia.features.categories.domain.usecase

import com.sample.factpedia.core.data.model.asEntity
import com.sample.factpedia.core.domain.model.Fact
import com.sample.factpedia.database.dao.FactDao
import javax.inject.Inject

class SyncFactsByCategoriesUseCase @Inject constructor(
    private val factDao: FactDao
) {
    suspend operator fun invoke(categoryId: Int ,facts: List<Fact>) {
        val entities = facts.map(Fact::asEntity)
        factDao.upsertFacts(entities)

        val remoteIds = entities.map { it.id }
        factDao.deleteFactsNotInCategory(categoryId, remoteIds)
    }
}