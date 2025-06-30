package com.sample.factpedia.features.categories.domain.repository

import com.sample.factpedia.core.common.result.DataError
import com.sample.factpedia.core.common.result.Response
import com.sample.factpedia.features.categories.data.repository.CategoryRepository
import com.sample.factpedia.features.categories.domain.model.Category
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class FakeCategoryRepository: CategoryRepository {
    var shouldFail: Boolean = false
    private val localCategories = mutableListOf<Category>()
    private var remoteCategories: List<Category> = emptyList()
    private var remoteError: DataError = DataError.Network.TIMEOUT

    private val categoriesFlow: MutableSharedFlow<List<Category>> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    override fun getCategoriesFromLocalDatabase(): Flow<List<Category>> = categoriesFlow

    override suspend fun loadRemoteCategories(): Response<List<Category>, DataError> {
        return if (shouldFail) {
            Response.Failure(remoteError)
        } else {
            Response.Success(remoteCategories)
        }
    }

    override suspend fun upsertCategories(categories: List<Category>) {
        this.localCategories.addAll(categories)
        categoriesFlow.emit(this.localCategories.toList())
    }

    override suspend fun deleteCategoriesNotIn(ids: List<Int>) {
        localCategories.removeAll { it.id !in ids }
        categoriesFlow.emit(localCategories.toList())
    }

    fun setRemoteCategories(categories: List<Category>) {
        this.remoteCategories = categories
    }

    fun setRemoteError(remoteError: DataError) {
      this.remoteError = remoteError
    }
}