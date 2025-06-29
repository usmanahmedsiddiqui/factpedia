package com.sample.factpedia.features.categories.data.repository

import com.sample.factpedia.core.model.data.FactApiModel

interface FactsByCategoryDataSource {
    suspend fun getFactsByCategoryId(categoryId: Int): List<FactApiModel>
}