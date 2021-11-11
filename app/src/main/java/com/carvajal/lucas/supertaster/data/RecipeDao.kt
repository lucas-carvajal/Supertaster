package com.carvajal.lucas.supertaster.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRecipe(recipe: Recipe): Long

    @Query("SELECT * FROM recipes")
    fun getAllRecipes(): List<Recipe>
}