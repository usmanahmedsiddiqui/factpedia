package com.sample.factpedia.features.categories.data.repository

import com.sample.factpedia.core.common.result.DataError
import com.sample.factpedia.core.common.result.Response
import com.sample.factpedia.core.domain.model.Fact
import com.sample.factpedia.features.categories.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getCategoriesFromLocalDatabase(): Flow<List<Category>>
    suspend fun loadRemoteCategories(): Response<List<Category>, DataError>
    suspend fun getFactsByCategoryId(categoryId: Int): Flow<List<Fact>>
    suspend fun loadRemoteFactsByCategoryId(categoryId: Int): Response<List<Fact>, DataError>
}