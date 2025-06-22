package com.sample.factpedia.features.search.domain.repository

import android.content.Context
import com.sample.factpedia.core.data.model.FactApiModel
import com.sample.factpedia.core.util.readAssetFile
import com.sample.factpedia.features.search.data.repository.SearchDataSource
import kotlinx.serialization.json.Json
import javax.inject.Inject

class LocalSearchDataSource @Inject constructor(
    private val context: Context,
    private val json: Json
): SearchDataSource {
    override suspend fun search(query: String): List<FactApiModel> {
        if(query.isBlank()) return emptyList()
        val jsonStr = context.readAssetFile("facts.json")
        val allFacts: List<FactApiModel> = json.decodeFromString(jsonStr)
        return allFacts.filter { fact ->
            fact.fact.contains(query, ignoreCase = true) ||
                    fact.categoryName.contains(query, ignoreCase = true)
        }
    }
}