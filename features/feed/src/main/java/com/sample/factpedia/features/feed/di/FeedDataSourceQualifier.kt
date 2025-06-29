package com.sample.factpedia.features.feed.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FeedLocalDataSource

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FeedRemoteDataSource