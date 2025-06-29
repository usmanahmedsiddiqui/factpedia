package com.sample.factpedia.core.model.mapper

import com.sample.factpedia.core.model.domain.BookmarkedFact
import com.sample.factpedia.core.model.domain.Fact

fun Fact.asBookmarkedFact(
    isBookmarked: Boolean
) = BookmarkedFact(
    id = this.id,
    fact = this.fact,
    categoryName = this.categoryName,
    categoryId = this.categoryId,
    isBookmarked = isBookmarked
)