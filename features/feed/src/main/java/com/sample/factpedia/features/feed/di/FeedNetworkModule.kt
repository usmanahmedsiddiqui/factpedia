package com.sample.factpedia.features.feed.di

import com.sample.factpedia.features.feed.data.api.FeedApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class FeedNetworkModule {
    @Provides
    fun provideFeedApi(retrofit: Retrofit): FeedApi {
        return retrofit.create(FeedApi::class.java)
    }
}