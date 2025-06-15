package com.sample.factpedia.core.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FactApiModel(
    @SerialName("id") val id: Int,
    @SerialName("fact") val fact: String,
    @SerialName("category_id") val categoryId: Int,
    @SerialName("category_name") val categoryName: String,
)