package com.carvajal.lucas.supertaster.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecipeStepDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRecipeStep(recipeStep: RecipeStep): Long

    @Query("SELECT * FROM recipe_steps WHERE recipe_id = :recipeId")
    fun getAllRecipeSteps(recipeId: Long): LiveData<List<RecipeStep>>

    @Query("SELECT * FROM recipe_steps WHERE recipe_id = :recipeId")
    fun getAllRecipeStepsStatic(recipeId: Long): List<RecipeStep>

    @Query("DELETE FROM recipe_steps WHERE recipe_id = :recipeId")
    fun deleteRecipeStepsByRecipeId(recipeId: Long)
}