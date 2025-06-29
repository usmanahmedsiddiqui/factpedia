package com.sample.factpedia.core.domain

import com.sample.factpedia.core.data.repository.BookmarksRepository
import javax.inject.Inject

class ToggleBookmarkUseCase @Inject constructor(
    private val bookmarksRepository: BookmarksRepository,
) {
    suspend operator fun invoke(factId: Int, isBookmarked: Boolean) {
        if (isBookmarked) {
            bookmarksRepository.addBookmark(factId)
        } else {
            bookmarksRepository.removeBookmark(factId)
        }
    }
}