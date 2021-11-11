package com.carvajal.lucas.supertaster.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "cookbook_recipes",
    foreignKeys = [
        ForeignKey(entity = Cookbook::class, parentColumns = ["id"], childColumns = ["cookbook_id"]),
        ForeignKey(entity = Recipe::class, parentColumns = ["id"], childColumns = ["recipe_id"])
    ]
)
data class CookbookRecipe(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "cookbook_id") val cookbookId: Long,
    @ColumnInfo(name = "recipe_id") val recipeId: Int,
)