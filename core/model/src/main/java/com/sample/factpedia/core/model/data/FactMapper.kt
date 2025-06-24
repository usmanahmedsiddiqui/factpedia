package com.sample.factpedia.core.model.data

import com.sample.factpedia.core.model.domain.Fact

fun FactApiModel.asDomainModel() = Fact(
    id = this.id,
    fact = this.fact,
    categoryName = this.categoryName,
    categoryId = this.categoryId,
)