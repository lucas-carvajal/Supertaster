package com.carvajal.lucas.supertaster.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecipeStepDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRecipeStep(vararg recipeStep: RecipeStep): Long

    @Query("SELECT * FROM recipe_steps WHERE recipe_id = :recipeId")
    fun getAllRecipeSteps(recipeId: Int): List<RecipeStep>
}