package com.sample.factpedia.features.search.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SearchLocalDataSource

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SearchRemoteDataSource