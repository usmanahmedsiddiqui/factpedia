package com.sample.factpedia.features.categories.data.repository

import com.sample.factpedia.core.common.result.DataError
import com.sample.factpedia.core.common.result.Response
import com.sample.factpedia.core.model.domain.Fact

interface FactsByCategoryRepository {
    suspend fun loadRemoteFactsByCategoryId(categoryId: Int): Response<List<Fact>, DataError>
}