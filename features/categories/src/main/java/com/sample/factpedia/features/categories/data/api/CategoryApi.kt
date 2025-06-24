package com.sample.factpedia.features.categories.data.api

import com.sample.factpedia.core.model.data.FactApiModel
import com.sample.factpedia.features.categories.data.model.CategoryApiModel
import retrofit2.http.GET
import retrofit2.http.Path

interface CategoryApi {
    @GET("/categories")
    suspend fun getCategories(): List<CategoryApiModel>

    @GET("/categories/{categoryId}/facts")
    suspend fun getFactsByCategoryId(@Path("categoryId") categoryId: Int): List<FactApiModel>
}