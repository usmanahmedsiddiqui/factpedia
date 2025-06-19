package com.sample.factpedia.features.bookmarks.domain.repository

import com.sample.factpedia.core.data.model.asDomainModel
import com.sample.factpedia.core.domain.model.Fact
import com.sample.factpedia.database.dao.FactDao
import com.sample.factpedia.features.bookmarks.data.repository.BookmarkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultBookmarkRepository @Inject constructor(
    private val factDao: FactDao,
) : BookmarkRepository {
    override fun getBookmarkedFacts(): Flow<List<Fact>> = factDao.getBookmarkedFacts().map { list -> list.map { it.asDomainModel(isBookmarked = true) } }
}