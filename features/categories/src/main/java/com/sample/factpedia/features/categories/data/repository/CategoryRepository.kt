package com.sample.factpedia.features.categories.data.repository

import com.sample.factpedia.core.domain.model.Fact
import com.sample.factpedia.features.categories.domain.model.Category

interface CategoryRepository {
    suspend fun getCategories(): List<Category>
    suspend fun getFactsByCategoryId(categoryId: Int): List<Fact>
}