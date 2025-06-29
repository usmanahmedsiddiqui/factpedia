package com.sample.factpedia.core.data.repository

import com.sample.factpedia.core.model.domain.Fact
import kotlinx.coroutines.flow.Flow

interface FactsRepository {
    fun getFactsByIds(ids: List<Int>): Flow<List<Fact>>
    fun getFactsByCategoryId(categoryId: Int): Flow<List<Fact>>
    suspend fun upsertFacts(facts: List<Fact>)
    suspend fun deleteFactsNotInCategory(categoryId: Int, factId: List<Int>)
    suspend fun getRandomFactExcluding(excludedId: Int): Fact?
    suspend fun getRandomFact(): Fact?
}