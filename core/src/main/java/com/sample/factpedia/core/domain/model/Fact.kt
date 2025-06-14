package com.sample.factpedia.core.domain.model

data class Fact(
    val id: Int,
    val fact: String,
    val categoryId: Int,
    val categoryName: String,
)