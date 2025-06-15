package com.sample.factpedia.features.categories.domain.repository

import android.content.Context
import com.sample.factpedia.core.data.model.FactApiModel
import com.sample.factpedia.features.categories.data.model.CategoryApiModel
import com.sample.factpedia.features.categories.data.repository.CategoryDataSource
import kotlinx.serialization.json.Json
import javax.inject.Inject

class LocalCategoryDataSource @Inject constructor(
    private val context: Context,
    private val json: Json
) : CategoryDataSource {
    override suspend fun getCategories(): List<CategoryApiModel> {
        val jsonStr = readAssetFile("categories.json")
        return json.decodeFromString(jsonStr)
    }

    override suspend fun getFactsByCategoryId(categoryId: Int): List<FactApiModel> {
        val jsonStr = readAssetFile("facts.json")
        val allFacts: List<FactApiModel> = json.decodeFromString(jsonStr)
        return allFacts.filter { it.categoryId == categoryId }
    }

    private fun readAssetFile(fileName: String): String {
        return context.assets.open(fileName).bufferedReader().use { it.readText() }
    }
}