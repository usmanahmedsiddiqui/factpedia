package com.sample.factpedia.features.feed.di

import com.sample.factpedia.features.feed.data.api.FactsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class FactNetworkModule {
    @Provides
    fun provideFeedApi(retrofit: Retrofit): FactsApi {
        return retrofit.create(FactsApi::class.java)
    }
}