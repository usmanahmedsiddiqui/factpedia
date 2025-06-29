package com.sample.factpedia.features.categories.domain.repository

import android.content.Context
import com.sample.factpedia.core.common.utils.readAssetFile
import com.sample.factpedia.core.model.data.FactApiModel
import com.sample.factpedia.features.categories.data.repository.FactsByCategoryDataSource
import kotlinx.serialization.json.Json
import javax.inject.Inject

class LocalFactsByCategoryDataSource @Inject constructor(
    private val context: Context,
    private val json: Json
): FactsByCategoryDataSource {
    override suspend fun getFactsByCategoryId(categoryId: Int): List<FactApiModel> {
        val jsonStr = context.readAssetFile("facts.json")
        val allFacts: List<FactApiModel> = json.decodeFromString(jsonStr)
        return allFacts.filter { it.categoryId == categoryId }
    }
}