package com.sample.factpedia.features.feed.data.api

import com.sample.factpedia.core.data.model.FactApiModel
import retrofit2.http.GET
import retrofit2.http.Path

interface FactsApi {
    @GET("/facts/{limit}")
    suspend fun getFacts(@Path("limit") limit: Int): List<FactApiModel>
}