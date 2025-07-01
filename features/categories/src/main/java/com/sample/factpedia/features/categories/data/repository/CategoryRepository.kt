package com.sample.factpedia.features.categories.data.repository

import com.sample.factpedia.core.common.result.DataError
import com.sample.factpedia.core.common.result.Response
import com.sample.factpedia.features.categories.domain.model.Category

interface CategoryRepository {
    suspend fun getCategoriesFromLocalDatabase(): List<Category>
    suspend fun loadRemoteCategories(): Response<List<Category>, DataError>
    suspend fun upsertCategories(categories: List<Category>)
    suspend fun deleteCategoriesNotIn(ids: List<Int>)
}