package com.sample.factpedia.core.model.domain

data class BookmarkedFact(
    val id: Int,
    val fact: String,
    val categoryId: Int,
    val categoryName: String,
    val isBookmarked: Boolean
)

