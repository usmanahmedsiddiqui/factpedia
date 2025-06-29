package com.sample.factpedia.core.testing.repository

import com.sample.factpedia.core.data.repository.FactsRepository
import com.sample.factpedia.core.model.domain.Fact
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map

class FakeFactRepository : FactsRepository {

    private val factsFlow: MutableSharedFlow<List<Fact>> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    override fun getFactsByIds(ids: List<Int>): Flow<List<Fact>> = factsFlow.map { facts ->
        facts.filter { it.id in ids }
    }

    override fun getFactsByCategoryId(categoryId: Int): Flow<List<Fact>> = factsFlow

    override suspend fun upsertFacts(facts: List<Fact>) = factsFlow.emit(facts)

    override suspend fun deleteFactsNotInCategory(categoryId: Int, factId: List<Int>) {}

    override suspend fun getRandomFactExcluding(excludedId: Int): Fact? = null

    override suspend fun getRandomFact(): Fact? = null


}