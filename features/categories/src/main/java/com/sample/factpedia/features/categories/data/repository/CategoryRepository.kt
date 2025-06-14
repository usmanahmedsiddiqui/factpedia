package com.sample.factpedia.features.categories.data.repository

import com.sample.factpedia.features.categories.domain.model.Category

interface CategoryRepository {
    suspend fun getCategories(): List<Category>
}