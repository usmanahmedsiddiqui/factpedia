package com.sample.factpedia.core.common.di

import com.sample.factpedia.core.common.dispatcher.CoroutineDispatcherProvider
import com.sample.factpedia.core.common.dispatcher.DefaultCoroutineDispatcherProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DispatcherModule {
    @Binds
    abstract fun bindCoroutineDispatcher(impl: DefaultCoroutineDispatcherProvider): CoroutineDispatcherProvider
}