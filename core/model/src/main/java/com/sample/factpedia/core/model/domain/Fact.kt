package com.sample.factpedia.core.model.domain

data class Fact(
    val id: Int,
    val fact: String,
    val categoryId: Int,
    val categoryName: String,
)