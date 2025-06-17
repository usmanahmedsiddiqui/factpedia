package com.sample.factpedia.features.categories.domain.usecase

import com.sample.factpedia.core.common.result.DataError
import com.sample.factpedia.core.common.result.Response
import com.sample.factpedia.features.categories.data.repository.CategoryRepository
import com.sample.factpedia.features.categories.domain.model.Category
import kotlinx.coroutines.delay
import javax.inject.Inject

class LoadRemoteCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
     suspend operator fun invoke(): Response<List<Category>, DataError> {
         delay(5000)
         return categoryRepository.loadRemoteCategories()
     }
}