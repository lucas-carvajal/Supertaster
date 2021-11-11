package com.carvajal.lucas.supertaster.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "recipe_images",
    foreignKeys = [
        ForeignKey(entity = Recipe::class, parentColumns = ["id"], childColumns = ["recipe_id"])
    ]
)
data class RecipeImage(
    @PrimaryKey @ColumnInfo(name = "recipe_id") val recipeId: Long,
    @ColumnInfo(name = "location") val location: String,
)