package com.sample.factpedia.features.categories.domain.usecase

import com.sample.factpedia.core.domain.model.Fact
import com.sample.factpedia.features.categories.data.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFactsByCategoryIdUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository,
) {
    suspend operator fun invoke(categoryId: Int): Flow<List<Fact>> = categoryRepository.getFactsByCategoryId(categoryId)
}