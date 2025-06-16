package com.sample.factpedia.features.search.data.repository

import com.sample.factpedia.core.data.model.FactApiModel

interface SearchDataSource {
    suspend fun search(query: String): List<FactApiModel>
}