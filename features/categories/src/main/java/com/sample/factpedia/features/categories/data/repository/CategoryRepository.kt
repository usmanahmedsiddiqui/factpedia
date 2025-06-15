package com.sample.factpedia.features.categories.data.repository

import com.sample.factpedia.core.common.result.DataError
import com.sample.factpedia.core.common.result.Response
import com.sample.factpedia.core.domain.model.Fact
import com.sample.factpedia.features.categories.domain.model.Category

interface CategoryRepository {
    suspend fun getCategories(): Response<List<Category>, DataError>
    suspend fun getFactsByCategoryId(categoryId: Int): Response<List<Fact>, DataError>
}