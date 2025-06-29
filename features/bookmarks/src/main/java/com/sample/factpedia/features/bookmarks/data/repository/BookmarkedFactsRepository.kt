package com.sample.factpedia.features.bookmarks.data.repository

import com.sample.factpedia.core.model.domain.BookmarkedFact
import kotlinx.coroutines.flow.Flow

interface BookmarkedFactsRepository {
    fun getBookmarkedFacts(): Flow<List<BookmarkedFact>>
    suspend fun removeBookmark(id: Int)
}