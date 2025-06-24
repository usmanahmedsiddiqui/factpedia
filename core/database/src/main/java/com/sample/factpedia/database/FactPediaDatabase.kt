package com.sample.factpedia.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sample.factpedia.database.dao.BookmarkDao
import com.sample.factpedia.database.dao.CategoryDao
import com.sample.factpedia.database.dao.FactDao
import com.sample.factpedia.database.model.BookmarkEntity
import com.sample.factpedia.database.model.CategoryEntity
import com.sample.factpedia.database.model.FactEntity

@Database(entities = [CategoryEntity::class, FactEntity::class, BookmarkEntity::class], version = 1)
abstract class FactPediaDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun factDao(): FactDao
    abstract fun bookmarkDao(): BookmarkDao
}