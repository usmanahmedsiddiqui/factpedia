package com.sample.factpedia.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.sample.factpedia.database.model.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Query("SELECT * FROM categories ORDER BY name ASC")
    fun getAllCategories(): Flow<List<CategoryEntity>>

    @Upsert
    suspend fun upsertTopics(categories: List<CategoryEntity>)

    @Query("DELETE FROM categories WHERE id not in (:categoriesId)")
    suspend fun deleteCategoriesNotIn(categoriesId: List<Int>)

    @Query("DELETE FROM categories")
    suspend fun clearCategories()
}