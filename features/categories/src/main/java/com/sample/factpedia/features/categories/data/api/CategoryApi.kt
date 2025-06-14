package com.sample.factpedia.features.categories.data.api

import com.sample.factpedia.core.network.data.model.FactApiModel
import com.sample.factpedia.features.categories.data.model.CategoryApiModel
import retrofit2.http.GET

interface CategoryApi {
    @GET("/categories")
    suspend fun getCategories(): List<CategoryApiModel>
}