package com.sample.factpedia.features.categories.di

import com.sample.factpedia.features.categories.data.repository.CategoryDataSource
import com.sample.factpedia.features.categories.data.repository.CategoryRepository
import com.sample.factpedia.features.categories.domain.repository.DefaultCategoryRepository
import com.sample.factpedia.features.categories.domain.repository.LocalCategoryDataSource
import com.sample.factpedia.features.categories.domain.repository.RemoteCategoryDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CategoryRepositoryModule {

    @Binds
    abstract fun bindCategoryRepository(repository: DefaultCategoryRepository): CategoryRepository

    @CategoryLocalDataSource
    @Binds
    abstract fun bindCategoryLocalDataSource(dataSource: LocalCategoryDataSource): CategoryDataSource

    @CategoryRemoteDataSource
    @Binds
    abstract fun bindCategoryRemoteDataSource(dataSource: RemoteCategoryDataSource): CategoryDataSource
}