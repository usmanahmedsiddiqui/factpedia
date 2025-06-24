package com.sample.factpedia.database.usecase

import com.sample.factpedia.database.dao.BookmarkDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ObserveBookmarkStatusUseCase @Inject constructor(
    private val bookmarkDao: BookmarkDao
) {
    operator fun invoke(factId: Int): Flow<Boolean> =
        bookmarkDao.getAllBookmarks().map { bookmarks ->
            bookmarks.any { it.factId == factId }
        }
}