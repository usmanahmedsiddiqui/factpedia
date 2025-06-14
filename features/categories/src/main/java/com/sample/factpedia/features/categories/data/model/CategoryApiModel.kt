package com.sample.factpedia.features.categories.data.model

import com.google.gson.annotations.SerializedName

data class CategoryApiModel(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
)