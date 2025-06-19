package com.sample.factpedia.features.bookmarks.domain.usecase

import com.sample.factpedia.core.domain.model.Fact
import com.sample.factpedia.features.bookmarks.data.repository.BookmarkRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBookmarkedFactsUseCase @Inject constructor(
    private val bookmarkRepository: BookmarkRepository
) {
    operator fun invoke(): Flow<List<Fact>> = bookmarkRepository.getBookmarkedFacts()
}