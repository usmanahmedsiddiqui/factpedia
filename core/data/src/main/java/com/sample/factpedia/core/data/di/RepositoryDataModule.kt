package com.sample.factpedia.core.data.di

import com.sample.factpedia.core.data.repository.BookmarksRepository
import com.sample.factpedia.core.data.repository.DefaultBookmarksRepository
import com.sample.factpedia.core.data.repository.DefaultFactsRepository
import com.sample.factpedia.core.data.repository.FactsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryDataModule {

    @Binds
    abstract fun bindsBookmarksRepository(repository: DefaultBookmarksRepository): BookmarksRepository

    @Binds
    abstract fun bindsFactsRepository(repository: DefaultFactsRepository): FactsRepository
}