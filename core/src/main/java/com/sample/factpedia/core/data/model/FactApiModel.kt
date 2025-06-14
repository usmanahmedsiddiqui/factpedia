package com.sample.factpedia.core.data.model

import com.google.gson.annotations.SerializedName

data class FactApiModel(
    @SerializedName("id") val id: Int,
    @SerializedName("fact") val fact: String,
    @SerializedName("category_id") val categoryId: Int,
    @SerializedName("category_name") val categoryName: String,
)