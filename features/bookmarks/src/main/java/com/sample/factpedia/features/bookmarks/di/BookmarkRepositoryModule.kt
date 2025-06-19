package com.sample.factpedia.features.bookmarks.di

import com.sample.factpedia.features.bookmarks.data.repository.BookmarkRepository
import com.sample.factpedia.features.bookmarks.domain.repository.DefaultBookmarkRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BookmarkRepositoryModule {
    @Binds
    abstract fun bindCategoryRepository(repository: DefaultBookmarkRepository): BookmarkRepository

}