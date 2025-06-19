package com.sample.factpedia.features.bookmarks.data.repository

import com.sample.factpedia.core.domain.model.Fact
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {
    fun getBookmarkedFacts(): Flow<List<Fact>>
}