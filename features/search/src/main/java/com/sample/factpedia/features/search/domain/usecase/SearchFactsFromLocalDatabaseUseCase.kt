package com.sample.factpedia.features.search.domain.usecase

import com.sample.factpedia.core.data.repository.BookmarksRepository
import com.sample.factpedia.core.data.repository.FactsRepository
import com.sample.factpedia.core.model.mapper.asBookmarkedFact
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchFactsFromLocalDatabaseUseCase @Inject constructor(
    private val factsRepository: FactsRepository,
    private val bookmarksRepository: BookmarksRepository,
) {
    operator fun invoke(query: String) = flow {
        val facts = factsRepository.searchFacts(query)
        emit(facts)
    }.combine(bookmarksRepository.getAllBookmarks()) { facts, bookmarkedIds ->
        facts.map { fact ->
            fact.asBookmarkedFact(isBookmarked = fact.id in bookmarkedIds)
        }
    }
}