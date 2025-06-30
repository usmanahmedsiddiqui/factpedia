package com.sample.factpedia.features.categories.domain.usecase

import com.sample.factpedia.features.categories.data.repository.CategoryRepository
import com.sample.factpedia.features.categories.domain.model.Category
import javax.inject.Inject

class SyncCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository,
) {
    suspend operator fun invoke(categories: List<Category>) {
        categoryRepository.upsertCategories(categories)
        val remoteIds = categories.map { it.id }
        categoryRepository.deleteCategoriesNotIn(remoteIds)
    }
}