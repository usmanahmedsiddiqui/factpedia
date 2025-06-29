package com.sample.factpedia.features.categories.data.repository

import com.sample.factpedia.core.common.result.DataError
import com.sample.factpedia.core.common.result.Response
import com.sample.factpedia.core.model.domain.BookmarkedFact
import com.sample.factpedia.core.model.domain.Fact
import kotlinx.coroutines.flow.Flow

interface FactsByCategoryRepository {
    fun getFactsByCategoryIdFromLocalDatabase(categoryId: Int): Flow<List<BookmarkedFact>>
    suspend fun loadRemoteFactsByCategoryId(categoryId: Int): Response<List<Fact>, DataError>
}