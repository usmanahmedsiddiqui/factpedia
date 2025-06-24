package com.sample.factpedia.features.feed.domain.usecase

import com.sample.factpedia.core.model.domain.Fact
import com.sample.factpedia.database.dao.FactDao
import com.sample.factpedia.database.model.asEntity
import javax.inject.Inject

class SyncFactsUseCase @Inject constructor(
    private val factDao: FactDao,
) {
    suspend operator fun invoke(facts: List<Fact>) {
        val entities = facts.map(Fact::asEntity)
        factDao.upsertFacts(entities)
    }
}