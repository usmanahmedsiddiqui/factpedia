package com.sample.factpedia.features.categories.domain.repository

import com.sample.factpedia.core.common.result.DataError
import com.sample.factpedia.core.common.result.Response
import com.sample.factpedia.core.common.result.handleError
import com.sample.factpedia.core.model.domain.Fact
import com.sample.factpedia.core.model.mapper.asDomainModel
import com.sample.factpedia.features.categories.data.repository.FactsByCategoryDataSource
import com.sample.factpedia.features.categories.data.repository.FactsByCategoryRepository
import com.sample.factpedia.features.categories.di.FactsByCategoryLocalDataSource
import kotlinx.coroutines.delay
import javax.inject.Inject

class DefaultFactsByCategoryRepository @Inject constructor(
    @FactsByCategoryLocalDataSource private val factsByCategoryDataSource: FactsByCategoryDataSource,
): FactsByCategoryRepository {
    override suspend fun loadRemoteFactsByCategoryId(categoryId: Int): Response<List<Fact>, DataError> {
        delay(5000)
        return handleError {
            factsByCategoryDataSource.getFactsByCategoryId(categoryId).map { it.asDomainModel() }
        }
    }
}