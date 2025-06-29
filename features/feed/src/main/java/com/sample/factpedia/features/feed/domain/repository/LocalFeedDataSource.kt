package com.sample.factpedia.features.feed.domain.repository

import android.content.Context
import com.sample.factpedia.core.common.utils.readAssetFile
import com.sample.factpedia.core.model.data.FactApiModel
import com.sample.factpedia.features.feed.data.repository.FeedDataSource
import kotlinx.serialization.json.Json
import javax.inject.Inject

class LocalFeedDataSource @Inject constructor(
    private val context: Context,
    private val json: Json
): FeedDataSource {
    override suspend fun getFeeds(limit: Int): List<FactApiModel> {
        val jsonStr = context.readAssetFile("facts.json")
        val allFacts: List<FactApiModel> = json.decodeFromString(jsonStr)
        return allFacts.shuffled().take(limit)
    }
}