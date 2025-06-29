package com.sample.factpedia.core.data.repository

import com.sample.factpedia.database.dao.BookmarkDao
import com.sample.factpedia.database.model.BookmarkEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultBookmarksRepository @Inject constructor(
    private val bookmarkDao: BookmarkDao

): BookmarksRepository {
    override suspend fun addBookmark(factId: Int) {
        bookmarkDao.addBookmark(BookmarkEntity(factId))
    }

    override suspend fun removeBookmark(factId: Int) {
        bookmarkDao.removeBookmark(factId)
    }

    override fun getAllBookmarks(): Flow<List<Int>> = bookmarkDao.getAllBookmarks().map { it.map(BookmarkEntity::factId) }

    override suspend fun isBookmarked(factId: Int): Boolean = bookmarkDao.isBookmarked(factId)
}