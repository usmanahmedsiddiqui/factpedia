package com.sample.factpedia.core.domain

import com.sample.factpedia.core.data.repository.BookmarksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ObserveBookmarkStatusUseCase @Inject constructor(
    private val bookmarksRepository: BookmarksRepository,
) {
    operator fun invoke(factId: Int): Flow<Boolean> =
        bookmarksRepository.getAllBookmarks().map { bookmarks ->
            bookmarks.any { it == factId }
        }
}