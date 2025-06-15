package com.sample.factpedia.features.categories.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CategoryLocalDataSource

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CategoryRemoteDataSource