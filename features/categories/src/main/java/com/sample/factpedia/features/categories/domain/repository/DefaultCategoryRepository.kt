package com.sample.factpedia.features.categories.domain.repository

import com.sample.factpedia.core.common.result.DataError
import com.sample.factpedia.core.common.result.Response
import com.sample.factpedia.core.common.result.handleError
import com.sample.factpedia.core.data.model.mapToDomain
import com.sample.factpedia.core.domain.model.Fact
import com.sample.factpedia.features.categories.data.model.mapToDomain
import com.sample.factpedia.features.categories.data.repository.CategoryDataSource
import com.sample.factpedia.features.categories.data.repository.CategoryRepository
import com.sample.factpedia.features.categories.di.CategoryLocalDataSource
import com.sample.factpedia.features.categories.di.CategoryRemoteDataSource
import com.sample.factpedia.features.categories.domain.model.Category
import javax.inject.Inject

class DefaultCategoryRepository @Inject constructor(
    @CategoryLocalDataSource private val categoryLocalDataSource: CategoryDataSource,
    @CategoryRemoteDataSource private val categoryRemoteDataSource: CategoryDataSource
) : CategoryRepository {
    override suspend fun getCategories(): Response<List<Category>, DataError> {
        return handleError {
            categoryLocalDataSource.getCategories().map { it.mapToDomain() }
        }
    }

    override suspend fun getFactsByCategoryId(categoryId: Int): Response<List<Fact>, DataError> {
        return handleError {
            categoryLocalDataSource.getFactsByCategoryId(categoryId).map { it.mapToDomain() }
        }
    }
}