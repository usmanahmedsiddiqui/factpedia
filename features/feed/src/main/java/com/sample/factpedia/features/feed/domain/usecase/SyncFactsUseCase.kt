package com.sample.factpedia.features.feed.domain.usecase

import com.sample.factpedia.core.data.model.asEntity
import com.sample.factpedia.core.domain.model.Fact
import com.sample.factpedia.database.dao.FactDao
import javax.inject.Inject

class SyncFactsUseCase @Inject constructor(
    private val factDao: FactDao,
) {
    suspend operator fun invoke(facts: List<Fact>) {
        val entities = facts.map(Fact::asEntity)
        factDao.upsertFacts(entities)
    }
}