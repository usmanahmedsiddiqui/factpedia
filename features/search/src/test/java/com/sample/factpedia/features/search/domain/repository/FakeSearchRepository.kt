package com.sample.factpedia.features.search.domain.repository

import com.sample.factpedia.core.common.result.DataError
import com.sample.factpedia.core.common.result.Response
import com.sample.factpedia.core.model.domain.Fact
import com.sample.factpedia.features.search.data.repository.SearchRepository
import kotlinx.coroutines.delay

class FakeSearchRepository: SearchRepository {
    private var shouldFail: Boolean = false
    private var remoteFacts: List<Fact> = emptyList()
    private var remoteError: DataError = DataError.Network.TIMEOUT

    override suspend fun search(query: String): Response<List<Fact>, DataError> {
        delay(10)
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