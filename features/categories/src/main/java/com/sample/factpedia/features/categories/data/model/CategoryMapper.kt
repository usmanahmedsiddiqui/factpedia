package com.sample.factpedia.features.categories.data.model

import com.sample.factpedia.features.categories.domain.model.Category

fun CategoryApiModel.mapToDomain() = Category(
    id = this.id, name = this.name
)