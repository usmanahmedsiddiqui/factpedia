package com.sample.factpedia.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.sample.factpedia.database.model.FactEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FactDao {
    @Upsert
    suspend fun upsertFacts(facts: List<FactEntity>)

    @Query("SELECT * FROM facts WHERE id != :excludedId ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomFactExcluding(excludedId: Int): FactEntity?

    @Query("SELECT * FROM facts ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomFact(): FactEntity?

    @Query("SELECT * FROM facts WHERE categoryId = :categoryId ORDER BY fact ASC")
    fun getFactsByCategoryId(categoryId: Int): Flow<List<FactEntity>>

    @Query("DELETE FROM facts WHERE categoryId = :categoryId and id not in (:factId)")
    suspend fun deleteFactsNotInCategory(categoryId: Int, factId: List<Int>)

    @Query("SELECT * FROM facts WHERE id IN (:ids)")
    fun getFactsByIds(ids: List<Int>): Flow<List<FactEntity>>

    @Query("""
    SELECT * FROM facts
    WHERE fact LIKE '%' || :query || '%' COLLATE NOCASE
       OR categoryName LIKE '%' || :query || '%' COLLATE NOCASE
""")
    suspend fun searchFacts(query: String): List<FactEntity>
}