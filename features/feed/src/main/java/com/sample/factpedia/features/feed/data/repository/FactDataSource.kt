package com.sample.factpedia.features.feed.data.repository

import com.sample.factpedia.core.data.model.FactApiModel

interface FactDataSource {
    suspend fun getFacts(limit: Int): List<FactApiModel>
}