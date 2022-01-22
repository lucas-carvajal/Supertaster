package com.carvajal.lucas.supertaster.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecipeIngredientDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRecipeIngredient(recipeIngredient: RecipeIngredient): Long

    @Query("SELECT * FROM recipe_ingredients WHERE recipe_id = :recipeId")
    fun getAllRecipeIngredients(recipeId: Long): LiveData<List<RecipeIngredient>>

    @Query("SELECT * FROM recipe_ingredients WHERE recipe_id = :recipeId")
    fun getAllRecipeIngredientsStatic(recipeId: Long): List<RecipeIngredient>
}
