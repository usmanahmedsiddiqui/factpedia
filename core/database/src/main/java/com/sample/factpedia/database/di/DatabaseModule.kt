package com.sample.factpedia.database.di

import android.content.Context
import androidx.room.Room
import com.sample.factpedia.database.FactPediaDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideFactPediaDatabase(@ApplicationContext context: Context): FactPediaDatabase =
        Room.databaseBuilder(
            context,
            FactPediaDatabase::class.java,
            "factpedia-database"
        ).build()
}