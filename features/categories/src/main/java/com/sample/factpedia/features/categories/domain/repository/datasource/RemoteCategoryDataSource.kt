package com.sample.factpedia.features.categories.domain.repository.datasource

import com.sample.factpedia.features.categories.data.api.CategoryApi
import com.sample.factpedia.features.categories.data.model.CategoryApiModel
import com.sample.factpedia.features.categories.data.repository.CategoryDataSource
import javax.inject.Inject

class RemoteCategoryDataSource @Inject constructor(
    private val categoryApi: CategoryApi,
) : CategoryDataSource {
    override suspend fun getCategories(): List<CategoryApiModel> =
        categoryApi.getCategories()
}