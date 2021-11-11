package com.carvajal.lucas.supertaster.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "recipe_steps",
    foreignKeys = [
        ForeignKey(entity = Recipe::class, parentColumns = ["id"], childColumns = ["recipe_id"])
    ]
)
data class RecipeStep(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "recipe_id") val recipeId: Long,
    @ColumnInfo(name = "sequence") val sequence: Int,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "extraNotes") val extraNotes: String,
)