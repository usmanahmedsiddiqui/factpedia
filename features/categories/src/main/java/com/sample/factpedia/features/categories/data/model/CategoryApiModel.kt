package com.sample.factpedia.features.categories.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryApiModel(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
)