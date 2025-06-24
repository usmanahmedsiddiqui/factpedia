package com.sample.factpedia.core.network.di

import com.sample.factpedia.core.network.interceptor.ResponseInterceptor

@dagger.Module
@dagger.hilt.InstallIn(dagger.hilt.components.SingletonComponent::class)
class NetworkModule {

    @dagger.Provides
    @javax.inject.Singleton
    fun provideLoggingInterceptor(): okhttp3.Interceptor {
        return okhttp3.logging.HttpLoggingInterceptor().apply {
            level = okhttp3.logging.HttpLoggingInterceptor.Level.BODY
        }
    }

    @dagger.Provides
    @javax.inject.Singleton
    fun provideOkHttpClient(
        loggingInterceptor: okhttp3.Interceptor,
        responseInterceptor: ResponseInterceptor,
    ): okhttp3.OkHttpClient {
        return okhttp3.OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(responseInterceptor)
            .build()
    }

    @dagger.Provides
    @javax.inject.Singleton
    fun provideRetrofit(
        okHttpClient: okhttp3.OkHttpClient
    ): retrofit2.Retrofit {
        return retrofit2.Retrofit.Builder()
            .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .build()
    }
}

private const val BASE_URL = "https://api.api-ninjas.com/v1/"