package com.sample.factpedia.features.categories.domain.usecase

import com.sample.factpedia.database.dao.CategoryDao
import com.sample.factpedia.features.categories.data.model.asEntity
import com.sample.factpedia.features.categories.domain.model.Category
import javax.inject.Inject

class SyncCategoriesUseCase @Inject constructor(
    private val categoryDao: CategoryDao,
) {
    suspend operator fun invoke(categories: List<Category>) {
        val entities = categories.map(Category::asEntity)
        categoryDao.upsertTopics(entities)

        val remoteIds = entities.map { it.id }
        categoryDao.deleteCategoriesNotIn(remoteIds)
    }
}