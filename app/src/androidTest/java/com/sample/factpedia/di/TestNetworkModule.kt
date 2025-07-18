package com.sample.factpedia.di

import com.sample.factpedia.core.network.di.NetworkModule
import com.sample.factpedia.core.network.interceptor.ResponseInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [NetworkModule::class]
)
class TestNetworkModule {

    @Provides
    @Singleton
    fun provideMockWebServer(): MockWebServer {
        return MockWebServer().apply { start(8080) }
    }

    @Provides
    @Singleton
    fun provideTestLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideTestOkHttpClient(
        loggingInterceptor: Interceptor,
        responseInterceptor: ResponseInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(responseInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideTestRetrofit(
        okHttpClient: OkHttpClient,
        mockWebServer: MockWebServer,
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(baseUrl(mockWebServer))
            .build()
    }

    private fun baseUrl(mockWebServer: MockWebServer): String =
        runBlocking(Dispatchers.IO) {
            mockWebServer.url("/").toString()
        }
}