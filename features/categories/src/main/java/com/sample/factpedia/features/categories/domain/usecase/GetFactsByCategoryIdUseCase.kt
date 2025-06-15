package com.sample.factpedia.features.categories.domain.usecase

import com.sample.factpedia.core.common.result.DataError
import com.sample.factpedia.core.common.result.Response
import com.sample.factpedia.core.domain.model.Fact
import com.sample.factpedia.features.categories.data.repository.CategoryRepository
import javax.inject.Inject

class GetFactsByCategoryIdUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(categoryId: Int): Response<List<Fact>, DataError> = categoryRepository.getFactsByCategoryId(categoryId)
}