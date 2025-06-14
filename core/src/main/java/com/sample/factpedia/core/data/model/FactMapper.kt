package com.sample.factpedia.core.data.model

import com.sample.factpedia.core.domain.model.Fact

fun FactApiModel.mapToDomain() = Fact(
    id = this.id,
    fact = this.fact,
    categoryName = this.categoryName,
    categoryId = this.categoryId,
)