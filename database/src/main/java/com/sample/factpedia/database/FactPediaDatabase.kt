package com.sample.factpedia.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sample.factpedia.database.dao.CategoryDao
import com.sample.factpedia.database.model.CategoryEntity

@Database(entities = [CategoryEntity::class], version = 1)
abstract class FactPediaDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
}