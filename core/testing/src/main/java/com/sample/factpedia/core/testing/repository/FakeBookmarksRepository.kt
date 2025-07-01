package com.sample.factpedia.core.testing.repository

import com.sample.factpedia.core.data.repository.BookmarksRepository
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class FakeBookmarksRepository: BookmarksRepository {
    private val bookmarkIds = mutableListOf<Int>()

    private val bookmarksFlow: MutableSharedFlow<List<Int>> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    init {
        bookmarksFlow.tryEmit(emptyList())
    }

    override suspend fun addBookmark(factId: Int) {
        if (!bookmarkIds.contains(factId)) {
            bookmarkIds.add(factId)
            bookmarksFlow.emit(bookmarkIds.toList())
        }
    }

    override suspend fun removeBookmark(factId: Int) {
        if (bookmarkIds.remove(factId)) {
            bookmarksFlow.emit(bookmarkIds.toList())
        }
    }

    override fun getAllBookmarks(): Flow<List<Int>> = bookmarksFlow

    override suspend fun isBookmarked(factId: Int): Boolean = false
}