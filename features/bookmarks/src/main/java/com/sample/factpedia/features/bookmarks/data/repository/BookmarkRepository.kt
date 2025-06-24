package com.sample.factpedia.features.bookmarks.data.repository

import com.sample.factpedia.core.model.domain.Fact
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {
    fun getBookmarkedFacts(): Flow<List<Fact>>
}