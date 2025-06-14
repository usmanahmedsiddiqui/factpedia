package com.sample.factpedia.features.categories.domain.repository

import com.sample.factpedia.features.categories.data.api.CategoryApi
import com.sample.factpedia.features.categories.data.model.mapToDomain
import com.sample.factpedia.features.categories.data.repository.CategoryRepository
import com.sample.factpedia.features.categories.domain.model.Category
import javax.inject.Inject

class DefaultCategoryRepository @Inject constructor(
    private val categoryApi: CategoryApi,
) : CategoryRepository {
    override suspend fun getCategories(): List<Category> {
        return categoryApi.getCategories().map { it.mapToDomain() }
    }
}