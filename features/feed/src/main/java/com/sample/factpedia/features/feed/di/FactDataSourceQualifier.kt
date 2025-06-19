package com.sample.factpedia.features.feed.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FactLocalDataSource

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FactRemoteDataSource