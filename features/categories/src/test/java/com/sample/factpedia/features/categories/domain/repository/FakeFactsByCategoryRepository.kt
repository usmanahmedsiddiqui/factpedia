package com.sample.factpedia.features.categories.domain.repository

import com.sample.factpedia.core.common.result.DataError
import com.sample.factpedia.core.common.result.Response
import com.sample.factpedia.core.model.domain.Fact
import com.sample.factpedia.features.categories.data.repository.FactsByCategoryRepository

class FakeFactsByCategoryRepository: FactsByCategoryRepository {
    private var shouldFail: Boolean = false
    private var remoteFacts: List<Fact> = emptyList()
    private var remoteError: DataError = DataError.Network.TIMEOUT

    override suspend fun loadRemoteFactsByCategoryId(categoryId: Int): Response<List<Fact>, DataError> {
        return if (shouldFail) {
            Response.Failure(remoteError)
        } else {
            Response.Success(remoteFacts)
        }
    }

    fun setRemoteFacts(facts: List<Fact>) {
        shouldFail = false
        this.remoteFacts = facts
    }

    fun setRemoteError(remoteError: DataError) {
        shouldFail = true
        this.remoteError = remoteError
    }
}