package com.sample.factpedia.features.feed.di

import com.sample.factpedia.features.feed.data.repository.FeedDataSource
import com.sample.factpedia.features.feed.data.repository.FeedRepository
import com.sample.factpedia.features.feed.domain.repository.DefaultFeedRepository
import com.sample.factpedia.features.feed.domain.repository.LocalFeedDataSource
import com.sample.factpedia.features.feed.domain.repository.RemoteFeedDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class FeedRepositoryModule {

    @Binds
    abstract fun bindFeedRepository(repository: DefaultFeedRepository): FeedRepository

    @FeedLocalDataSource
    @Binds
    abstract fun bindFeedLocalDataSource(dataSource: LocalFeedDataSource): FeedDataSource

    @FeedRemoteDataSource
    @Binds
    abstract fun bindFeedRemoteDataSource(dataSource: RemoteFeedDataSource): FeedDataSource
}