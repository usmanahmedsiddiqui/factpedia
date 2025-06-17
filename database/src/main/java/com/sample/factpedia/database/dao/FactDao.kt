package com.sample.factpedia.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.sample.factpedia.database.model.FactEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FactDao {

    @Query("SELECT * FROM facts ORDER BY fact ASC")
    fun getAllFacts(): Flow<List<FactEntity>>

    @Query("SELECT * FROM facts WHERE categoryId = :categoryId ORDER BY fact ASC")
    fun getFactsByCategoryId(categoryId: Int): Flow<List<FactEntity>>

    @Upsert
    suspend fun upsertFacts(facts: List<FactEntity>)

    @Query("DELETE FROM facts WHERE categoryId = :categoryId and id not in (:factId)")
    suspend fun deleteFactsNotInCategory(categoryId: Int, factId: List<Int>)

    @Query("DELETE FROM facts")
    suspend fun clearFacts()
}