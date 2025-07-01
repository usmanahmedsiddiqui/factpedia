package com.sample.factpedia.features.categories.domain.repository

import com.sample.factpedia.core.common.result.DataError
import com.sample.factpedia.core.common.result.Response
import com.sample.factpedia.core.common.result.handleError
import com.sample.factpedia.database.dao.CategoryDao
import com.sample.factpedia.database.model.CategoryEntity
import com.sample.factpedia.features.categories.data.model.CategoryApiModel
import com.sample.factpedia.features.categories.data.model.asDomainModel
import com.sample.factpedia.features.categories.data.model.asEntity
import com.sample.factpedia.features.categories.data.repository.CategoryDataSource
import com.sample.factpedia.features.categories.data.repository.CategoryRepository
import com.sample.factpedia.features.categories.di.CategoryLocalDataSource
import com.sample.factpedia.features.categories.domain.model.Category
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultCategoryRepository @Inject constructor(
    @CategoryLocalDataSource private val categoryDataSource: CategoryDataSource,
    private val categoryDao: CategoryDao,
) : CategoryRepository {

    override fun getCategoriesFromLocalDatabase(): Flow<List<Category>> =
        categoryDao.getAllCategories().map { list -> list.map(CategoryEntity::asDomainModel) }

    override suspend fun loadRemoteCategories(): Response<List<Category>, DataError> {
        delay(5000)
        return handleError {
            categoryDataSource.getCategories().map(CategoryApiModel::asDomainModel)
        }
    }

    override suspend fun upsertCategories(categories: List<Category>) =
        categoryDao.upsertCategories(categories.map(Category::asEntity))

    override suspend fun deleteCategoriesNotIn(ids: List<Int>) =
        categoryDao.deleteCategoriesNotIn(ids)
}