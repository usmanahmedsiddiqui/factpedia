package com.sample.factpedia.features.search.data.repository

import com.sample.factpedia.features.search.di.SearchLocalDataSource
import com.sample.factpedia.features.search.di.SearchRemoteDataSource
import com.sample.factpedia.features.search.domain.repository.DefaultSearchRepository
import com.sample.factpedia.features.search.domain.repository.LocalSearchDataSource
import com.sample.factpedia.features.search.domain.repository.RemoteSearchDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SearchRepositoryModule {

    @Binds
    abstract fun bindSearchRepository(repository: DefaultSearchRepository): SearchRepository

    @SearchLocalDataSource
    @Binds
    abstract fun bindSearchLocalDataSource(dataSource: LocalSearchDataSource): SearchDataSource

    @SearchRemoteDataSource
    @Binds
    abstract fun bindSearchRemoteDataSource(dataSource: RemoteSearchDataSource): SearchDataSource
}