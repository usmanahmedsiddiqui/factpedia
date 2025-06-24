package com.sample.factpedia.features.categories.domain.repository

import com.sample.factpedia.core.common.result.DataError
import com.sample.factpedia.core.common.result.Response
import com.sample.factpedia.core.common.result.handleError
import com.sample.factpedia.core.model.data.asDomainModel
import com.sample.factpedia.core.model.domain.Fact
import com.sample.factpedia.database.dao.BookmarkDao
import com.sample.factpedia.database.dao.CategoryDao
import com.sample.factpedia.database.dao.FactDao
import com.sample.factpedia.database.model.CategoryEntity
import com.sample.factpedia.database.model.asDomainModel
import com.sample.factpedia.features.categories.data.model.CategoryApiModel
import com.sample.factpedia.features.categories.data.model.asDomainModel
import com.sample.factpedia.features.categories.data.repository.CategoryDataSource
import com.sample.factpedia.features.categories.data.repository.CategoryRepository
import com.sample.factpedia.features.categories.di.CategoryLocalDataSource
import com.sample.factpedia.features.categories.domain.model.Category
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultCategoryRepository @Inject constructor(
    @CategoryLocalDataSource private val categoryDataSource: CategoryDataSource,
    private val categoryDao: CategoryDao,
    private val factDao: FactDao,
    private val bookmarkDao: BookmarkDao,
) : CategoryRepository {

    override fun getCategoriesFromLocalDatabase(): Flow<List<Category>> =
        categoryDao.getAllCategories().map { list -> list.map(CategoryEntity::asDomainModel) }

    override suspend fun loadRemoteCategories(): Response<List<Category>, DataError> {
        delay(5000)
        return handleError {
            categoryDataSource.getCategories().map(CategoryApiModel::asDomainModel)
        }
    }

    override fun getFactsByCategoryIdFromLocalDatabase(categoryId: Int): Flow<List<Fact>> =
        combine(
            factDao.getFactsByCategoryId(categoryId),
            bookmarkDao.getAllBookmarks()
        ) { facts, bookmarks ->
            val bookmarkedIds = bookmarks.map { it.factId }.toSet()
            facts.map { it.asDomainModel(isBookmarked = it.id in bookmarkedIds) }
        }

    override suspend fun loadRemoteFactsByCategoryId(categoryId: Int): Response<List<Fact>, DataError> {
        delay(5000)
        return handleError {
            categoryDataSource.getFactsByCategoryId(categoryId).map { it.asDomainModel() }
        }
    }
}