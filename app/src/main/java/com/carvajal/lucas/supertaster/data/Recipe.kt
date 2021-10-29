package com.carvajal.lucas.supertaster.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class Recipe(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "cuisine") val cuisine: String,
    @ColumnInfo(name = "type_of_meal") val typeOfMeal: String,
    @ColumnInfo(name = "servings") val servings: Int,
    @ColumnInfo(name = "prep_time") val prepTime: Int,
    @ColumnInfo(name = "cook_time") val cookTime: String,
)