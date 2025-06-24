package com.sample.factpedia.database.dao

import com.sample.factpedia.database.FactPediaDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {
    @Provides
    fun providesCategoryDao(
        database: FactPediaDatabase,
    ): CategoryDao = database.categoryDao()

    @Provides
    fun providesFactDao(
        database: FactPediaDatabase,
    ): FactDao = database.factDao()

    @Provides
    fun providesBookMarkDao(
        database: FactPediaDatabase,
    ): BookmarkDao = database.bookmarkDao()
}