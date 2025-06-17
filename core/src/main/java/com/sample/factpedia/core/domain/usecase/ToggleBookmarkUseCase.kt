package com.sample.factpedia.core.domain.usecase

import com.sample.factpedia.database.dao.BookmarkDao
import com.sample.factpedia.database.model.BookmarkEntity
import javax.inject.Inject

class ToggleBookmarkUseCase @Inject constructor(
    private val bookmarkDao: BookmarkDao,
) {
    suspend operator fun invoke(factId: Int, isBookmarked: Boolean) {
        if (isBookmarked) {
            bookmarkDao.addBookmark(BookmarkEntity(factId))
        } else {
            bookmarkDao.removeBookmark(factId)
        }
    }
}