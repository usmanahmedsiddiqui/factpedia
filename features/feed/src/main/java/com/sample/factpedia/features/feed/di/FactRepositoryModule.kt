package com.sample.factpedia.features.feed.di

import com.sample.factpedia.features.feed.data.repository.FactDataSource
import com.sample.factpedia.features.feed.data.repository.FactRepository
import com.sample.factpedia.features.feed.domain.repository.DefaultFactRepository
import com.sample.factpedia.features.feed.domain.repository.LocalFactDataSource
import com.sample.factpedia.features.feed.domain.repository.RemoteFactDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class FactRepositoryModule {

    @Binds
    abstract fun bindFactRepository(repository: DefaultFactRepository): FactRepository

    @FactLocalDataSource
    @Binds
    abstract fun bindFactLocalDataSource(dataSource: LocalFactDataSource): FactDataSource

    @FactRemoteDataSource
    @Binds
    abstract fun bindFactRemoteDataSource(dataSource: RemoteFactDataSource): FactDataSource
}