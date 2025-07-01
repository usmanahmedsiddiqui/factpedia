package com.sample.factpedia.features.categories.di

import com.sample.factpedia.features.categories.data.repository.FactsByCategoryDataSource
import com.sample.factpedia.features.categories.data.repository.FactsByCategoryRepository
import com.sample.factpedia.features.categories.domain.repository.DefaultFactsByCategoryRepository
import com.sample.factpedia.features.categories.domain.repository.datasource.LocalFactsByCategoryDataSource
import com.sample.factpedia.features.categories.domain.repository.datasource.RemoteFactsByCategoryDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class FactsByCategoryRepositoryModule {
    @Binds
    abstract fun bindFactsByCategoryRepository(repository: DefaultFactsByCategoryRepository): FactsByCategoryRepository

    @FactsByCategoryLocalDataSource
    @Binds
    abstract fun bindFactsByCategoryLocalDataSource(dataSource: LocalFactsByCategoryDataSource): FactsByCategoryDataSource

    @FactsByCategoryRemoteDataSource
    @Binds
    abstract fun bindFactsByCategoryRemoteDataSource(dataSource: RemoteFactsByCategoryDataSource): FactsByCategoryDataSource
}