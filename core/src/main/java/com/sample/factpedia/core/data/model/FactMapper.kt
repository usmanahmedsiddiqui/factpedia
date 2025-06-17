package com.sample.factpedia.core.data.model

import com.sample.factpedia.core.domain.model.Fact
import com.sample.factpedia.database.model.FactEntity

fun FactApiModel.asDomainModel() = Fact(
    id = this.id,
    fact = this.fact,
    categoryName = this.categoryName,
    categoryId = this.categoryId,
)

fun FactEntity.asDomainModel() = Fact(
    id = this.id,
    fact = this.fact,
    categoryName = this.categoryName,
    categoryId = this.categoryId,
)

fun Fact.asEntity() = FactEntity(
    id = this.id,
    fact = this.fact,
    categoryName = this.categoryName,
    categoryId = this.categoryId,
)