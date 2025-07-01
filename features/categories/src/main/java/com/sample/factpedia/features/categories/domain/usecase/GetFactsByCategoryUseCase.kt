package com.sample.factpedia.features.categories.domain.usecase

import com.sample.factpedia.core.data.repository.BookmarksRepository
import com.sample.factpedia.core.data.repository.FactsRepository
import com.sample.factpedia.core.model.mapper.asBookmarkedFact
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetFactsByCategoryUseCase @Inject constructor(
    private val factsRepository: FactsRepository,
    private val bookmarksRepository: BookmarksRepository,
) {
    operator fun invoke(categoryId: Int) = combine(
        factsRepository.getFactsByCategoryId(categoryId),
        bookmarksRepository.getAllBookmarks()
    ) { facts, bookmarkedIds ->
        facts.map { it.asBookmarkedFact(isBookmarked = it.id in bookmarkedIds) }
    }

}