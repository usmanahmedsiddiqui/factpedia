package com.sample.factpedia.core.testing.repository

import com.sample.factpedia.core.data.repository.FactsRepository
import com.sample.factpedia.core.model.domain.Fact
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map

class FakeFactRepository : FactsRepository {

    private val localFacts = mutableListOf<Fact>()
    private val factsFlow: MutableSharedFlow<List<Fact>> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    init {
        factsFlow.tryEmit(emptyList())
    }

    override fun getFactsByIds(ids: List<Int>): Flow<List<Fact>> = factsFlow.map { facts ->
        facts.filter { it.id in ids }
    }

    override fun getFactsByCategoryId(categoryId: Int): Flow<List<Fact>> = factsFlow.map { facts ->
        facts.filter { it.categoryId == categoryId }
    }

    override suspend fun upsertFacts(facts: List<Fact>) {
        val existing = localFacts.associateBy { it.id }.toMutableMap()

        for (fact in facts) {
            existing[fact.id] = fact
        }

        localFacts.clear()
        localFacts.addAll(existing.values)

        factsFlow.emit(localFacts.toList())
    }

    override suspend fun deleteFactsNotInCategory(categoryId: Int, factId: List<Int>) {
        localFacts.removeAll { it.id !in factId }
        factsFlow.emit(localFacts.toList())
    }

    override suspend fun getRandomFactExcluding(excludedId: Int): Fact? =
        localFacts.firstOrNull { it.id != excludedId }

    override suspend fun getRandomFact(): Fact? = localFacts.firstOrNull()

    override suspend fun searchFacts(query: String): List<Fact> =
        localFacts.filter { fact ->
            fact.fact.contains(query, ignoreCase = true) ||
                    fact.categoryName.contains(query, ignoreCase = true)
        }
}