package com.sample.factpedia.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sample.factpedia.core.model.domain.Fact

@Entity(
    tableName = "facts"
)
data class FactEntity (
    @PrimaryKey
    val id: Int,
    val fact: String,
    val categoryId: Int,
    val categoryName: String,
    val createdAt: Long = System.currentTimeMillis()
)

fun FactEntity.asDomainModel() = Fact(
    id = this.id,
    fact = this.fact,
    categoryName = this.categoryName,
    categoryId = this.categoryId,
)

fun Fact.asEntity() = FactEntity(
    id = this.id,
    fact = this.fact,
    categoryName = this.categoryName,
    categoryId = this.categoryId,
)