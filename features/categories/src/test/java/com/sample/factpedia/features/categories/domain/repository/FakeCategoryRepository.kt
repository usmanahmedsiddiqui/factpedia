package com.sample.factpedia.features.categories.domain.repository

import com.sample.factpedia.core.common.result.DataError
import com.sample.factpedia.core.common.result.Response
import com.sample.factpedia.features.categories.data.repository.CategoryRepository
import com.sample.factpedia.features.categories.domain.model.Category
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class FakeCategoryRepository : CategoryRepository {
    private var shouldFail: Boolean = false
    private val localCategories = mutableListOf<Category>()
    private var remoteCategories: List<Category> = emptyList()
    private var remoteError: DataError = DataError.Network.TIMEOUT

    private val categoriesFlow: MutableSharedFlow<List<Category>> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    init {
        categoriesFlow.tryEmit(emptyList())
    }

    override fun getCategoriesFromLocalDatabase(): Flow<List<Category>> = categoriesFlow

    override suspend fun loadRemoteCategories(): Response<List<Category>, DataError> {
        delay(10)
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

        categoriesFlow.emit(localCategories.toList())
    }

    override suspend fun deleteCategoriesNotIn(ids: List<Int>) {
        localCategories.removeAll { it.id !in ids }
        categoriesFlow.emit(localCategories.toList())
    }

    fun setRemoteCategories(categories: List<Category>) {
        shouldFail = false
        this.remoteCategories = categories
    }

    fun setRemoteError(remoteError: DataError) {
        shouldFail = true
        this.remoteError = remoteError
    }
}