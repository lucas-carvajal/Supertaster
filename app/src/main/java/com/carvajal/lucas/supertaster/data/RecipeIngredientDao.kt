package com.carvajal.lucas.supertaster.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecipeIngredientDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRecipeIngredient(vararg recipeIngredient: RecipeIngredient): Long

    @Query("SELECT * FROM recipe_ingredients WHERE recipe_id = :recipeId")
    fun getAllRecipeIngredients(recipeId: Int): List<RecipeIngredient>
}
