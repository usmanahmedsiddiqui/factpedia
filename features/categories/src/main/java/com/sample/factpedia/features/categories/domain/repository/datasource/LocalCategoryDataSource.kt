package com.sample.factpedia.features.categories.domain.repository.datasource

import android.content.Context
import com.sample.factpedia.core.common.utils.readAssetFile
import com.sample.factpedia.features.categories.data.model.CategoryApiModel
import com.sample.factpedia.features.categories.data.repository.CategoryDataSource
import kotlinx.serialization.json.Json
import javax.inject.Inject

class LocalCategoryDataSource @Inject constructor(
    private val context: Context,
    private val json: Json
) : CategoryDataSource {
    override suspend fun getCategories(): List<CategoryApiModel> {
        val jsonStr = context.readAssetFile("categories.json")
        return json.decodeFromString(jsonStr)
    }
}