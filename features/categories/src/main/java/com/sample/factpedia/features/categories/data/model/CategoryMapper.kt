package com.sample.factpedia.features.categories.data.model

import com.sample.factpedia.database.model.CategoryEntity
import com.sample.factpedia.features.categories.domain.model.Category

fun CategoryApiModel.asDomainModel() = Category(
    id = this.id, name = this.name
)

fun CategoryEntity.asDomainModel() = Category(
    id = this.id, name = this.name
)

fun Category.asEntity() = CategoryEntity(
    id = this.id, name = this.name
)
