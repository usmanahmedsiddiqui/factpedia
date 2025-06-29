package com.sample.factpedia.features.bookmarks.di

import com.sample.factpedia.features.bookmarks.data.repository.BookmarkedFactsRepository
import com.sample.factpedia.features.bookmarks.domain.repository.DefaultBookmarkedFactsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BookmarkedFactsRepositoryModule {
    @Binds
    abstract fun bindBookmarkedFactRepository(repository: DefaultBookmarkedFactsRepository): BookmarkedFactsRepository
}