package com.sample.factpedia.features.categories.domain.repository

import com.sample.factpedia.core.model.data.FactApiModel
import com.sample.factpedia.features.categories.data.api.CategoryApi
import com.sample.factpedia.features.categories.data.repository.FactsByCategoryDataSource
import javax.inject.Inject

class RemoteFactsByCategoryDataSource @Inject constructor(
    private val categoryApi: CategoryApi,
): FactsByCategoryDataSource {
    override suspend fun getFactsByCategoryId(categoryId: Int): List<FactApiModel> {
        return categoryApi.getFactsByCategoryId(categoryId)
    }
}