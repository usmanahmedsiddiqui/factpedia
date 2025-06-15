package com.sample.factpedia.features.categories.domain.repository

import com.sample.factpedia.core.common.result.DataError
import com.sample.factpedia.core.common.result.Response
import com.sample.factpedia.core.common.result.handleError
import com.sample.factpedia.core.data.model.mapToDomain
import com.sample.factpedia.core.domain.model.Fact
import com.sample.factpedia.features.categories.data.api.CategoryApi
import com.sample.factpedia.features.categories.data.model.mapToDomain
import com.sample.factpedia.features.categories.data.repository.CategoryRepository
import com.sample.factpedia.features.categories.domain.model.Category
import javax.inject.Inject

class DefaultCategoryRepository @Inject constructor(
    private val categoryApi: CategoryApi,
) : CategoryRepository {
    override suspend fun getCategories(): Response<List<Category>, DataError> {
        return handleError {
            categoryApi.getCategories().map { it.mapToDomain() }
        }
    }

    override suspend fun getFactsByCategoryId(categoryId: Int): Response<List<Fact>, DataError> {
        return handleError {
            categoryApi.getFactsByCategoryId(categoryId).map { it.mapToDomain() }
        }
    }
}