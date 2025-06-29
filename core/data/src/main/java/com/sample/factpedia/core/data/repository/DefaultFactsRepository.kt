package com.sample.factpedia.core.data.repository

import com.sample.factpedia.core.model.domain.Fact
import com.sample.factpedia.database.dao.FactDao
import com.sample.factpedia.database.model.FactEntity
import com.sample.factpedia.database.model.asDomainModel
import com.sample.factpedia.database.model.asEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultFactsRepository @Inject constructor(
    private val factDao: FactDao,
) : FactsRepository {
    override fun getFactsByIds(ids: List<Int>): Flow<List<Fact>> =
        factDao.getFactsByIds(ids).map { list -> list.map(FactEntity::asDomainModel) }

    override fun getFactsByCategoryId(categoryId: Int): Flow<List<Fact>> =
        factDao.getFactsByCategoryId(categoryId).map { list -> list.map(FactEntity::asDomainModel) }

    override suspend fun upsertFacts(facts: List<Fact>) {
        val entities = facts.map(Fact::asEntity)
        factDao.upsertFacts(entities)
    }

    override suspend fun deleteFactsNotInCategory(categoryId: Int, factId: List<Int>) {
        factDao.deleteFactsNotInCategory(categoryId, factId)
    }

    override suspend fun getRandomFactExcluding(excludedId: Int): Fact? =
        factDao.getRandomFactExcluding(excludedId)?.asDomainModel()

    override suspend fun getRandomFact(): Fact? = factDao.getRandomFact()?.asDomainModel()
}