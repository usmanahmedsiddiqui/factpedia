package com.sample.factpedia.features.categories.domain.repository

import com.sample.factpedia.core.common.result.DataError
import com.sample.factpedia.core.common.result.Response
import com.sample.factpedia.features.categories.data.repository.CategoryRepository
import com.sample.factpedia.features.categories.domain.model.Category

class FakeCategoryRepository : CategoryRepository {
    private var shouldFail: Boolean = false
    private val localCategories = mutableListOf<Category>()
    private var remoteCategories: List<Category> = emptyList()
    private var remoteError: DataError = DataError.Network.TIMEOUT

    override suspend fun getCategoriesFromLocalDatabase(): List<Category> = localCategories

    override suspend fun loadRemoteCategories(): Response<List<Category>, DataError> {
        return if (shouldFail) {
            Response.Failure(remoteError)
        } else {
            Response.Success(remoteCategories)
        }
    }

    override suspend fun upsertCategories(categories: List<Category>) {
        val existing = localCategories.associateBy { it.id }.toMutableMap()

        for (category in categories) {
            existing[category.id] = category
        }

        localCategories.clear()
        localCategories.addAll(existing.values)
    }

    override suspend fun deleteCategoriesNotIn(ids: List<Int>) {
        localCategories.removeAll { it.id !in ids }
    }

    fun setRemoteCategories(categories: List<Category>) {
        this.remoteCategories = categories
        shouldFail = false
    }

    fun setRemoteError(remoteError: DataError) {
        this.remoteError = remoteError
        shouldFail = true
    }
}