package com.sample.factpedia.core.di

import com.sample.factpedia.core.common.CoroutineDispatcherProvider
import com.sample.factpedia.core.common.DefaultCoroutineDispatcherProvider
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DispatcherModule {
    abstract fun bindCoroutineDispatcher(impl: DefaultCoroutineDispatcherProvider): CoroutineDispatcherProvider
}