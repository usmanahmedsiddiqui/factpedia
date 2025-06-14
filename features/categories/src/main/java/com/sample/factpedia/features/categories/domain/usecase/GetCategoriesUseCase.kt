package com.sample.factpedia.features.categories.domain.usecase

import com.sample.factpedia.features.categories.data.repository.CategoryRepository
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke() = categoryRepository.getCategories()
}