package com.sample.factpedia.features.categories.data.repository

import com.sample.factpedia.features.categories.data.model.CategoryApiModel

interface CategoryDataSource {
    suspend fun getCategories(): List<CategoryApiModel>
}