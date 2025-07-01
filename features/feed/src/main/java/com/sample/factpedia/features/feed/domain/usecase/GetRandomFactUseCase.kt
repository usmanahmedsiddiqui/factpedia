package com.sample.factpedia.features.feed.domain.usecase

import com.sample.factpedia.core.data.repository.BookmarksRepository
import com.sample.factpedia.core.data.repository.FactsRepository
import com.sample.factpedia.core.model.domain.BookmarkedFact
import com.sample.factpedia.core.model.mapper.asBookmarkedFact
import javax.inject.Inject

class GetRandomFactUseCase @Inject constructor(
    private val factsRepository: FactsRepository,
    private val bookmarksRepository: BookmarksRepository,
    ) {
    suspend operator fun invoke(factId: Int?): BookmarkedFact? {
        val fact = if (factId != null) {
            factsRepository.getRandomFactExcluding(factId) ?: factsRepository.getRandomFact()
        } else {
            factsRepository.getRandomFact()
        }
        fact ?: return null

        val isBookmarked = bookmarksRepository.isBookmarked(fact.id)
        return fact.asBookmarkedFact(isBookmarked)
    }
}