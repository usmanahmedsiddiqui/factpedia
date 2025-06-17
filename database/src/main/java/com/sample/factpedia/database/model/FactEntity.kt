package com.sample.factpedia.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "facts"
)
data class FactEntity (
    @PrimaryKey
    val id: Int,
    val fact: String,
    val categoryId: Int,
    val categoryName: String,
)