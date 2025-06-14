package com.sample.factpedia.features.categories.domain.usecase

import com.sample.factpedia.features.categories.data.repository.CategoryRepository
import javax.inject.Inject

class GetFactsByCategoryIdUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(categoryId: Int) = categoryRepository.getFactsByCategoryId(categoryId)
}