package com.sample.factpedia.features.categories.domain.usecase

import com.sample.factpedia.core.common.result.DataError
import com.sample.factpedia.core.common.result.Response
import com.sample.factpedia.core.domain.model.Fact
import com.sample.factpedia.features.categories.data.repository.CategoryRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class LoadRemoteFactsByCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(categoryId: Int): Response<List<Fact>, DataError> {
        delay(5000)
        return categoryRepository.loadRemoteFactsByCategoryId(categoryId)
    }
}