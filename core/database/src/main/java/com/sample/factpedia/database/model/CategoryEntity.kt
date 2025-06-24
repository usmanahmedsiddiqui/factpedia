package com.sample.factpedia.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "categories"
)
data class CategoryEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
)