package com.sample.factpedia.features.feed.domain.repository

import android.content.Context
import com.sample.factpedia.core.data.model.FactApiModel
import com.sample.factpedia.core.util.readAssetFile
import com.sample.factpedia.features.feed.data.repository.FactDataSource
import kotlinx.serialization.json.Json
import javax.inject.Inject

class LocalFactDataSource @Inject constructor(
    private val context: Context,
    private val json: Json
): FactDataSource {
    override suspend fun getFacts(limit: Int): List<FactApiModel> {
        val jsonStr = context.readAssetFile("facts.json")
        val allFacts: List<FactApiModel> = json.decodeFromString(jsonStr)
        return allFacts.shuffled().take(limit)
    }
}