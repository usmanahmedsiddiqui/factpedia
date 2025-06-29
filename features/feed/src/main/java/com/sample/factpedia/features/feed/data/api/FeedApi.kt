package com.sample.factpedia.features.feed.data.api

import com.sample.factpedia.core.model.data.FactApiModel
import retrofit2.http.GET
import retrofit2.http.Path

interface FeedApi {
    @GET("/facts/{limit}")
    suspend fun getFeeds(@Path("limit") limit: Int): List<FactApiModel>
}