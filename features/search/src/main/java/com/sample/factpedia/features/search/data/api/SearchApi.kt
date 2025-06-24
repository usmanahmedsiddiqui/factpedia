package com.sample.factpedia.features.search.data.api

import com.sample.factpedia.core.model.data.FactApiModel
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {
    @GET("facts/search")
    suspend fun searchFacts(@Query("query") query: String): List<FactApiModel>
}