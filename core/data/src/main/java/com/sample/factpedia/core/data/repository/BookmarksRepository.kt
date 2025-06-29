package com.sample.factpedia.core.data.repository

import kotlinx.coroutines.flow.Flow

interface BookmarksRepository {
    suspend fun addBookmark(factId:Int)
    suspend fun removeBookmark(factId:Int)
    fun getAllBookmarks(): Flow<List<Int>>
    suspend fun isBookmarked(factId: Int): Boolean
}