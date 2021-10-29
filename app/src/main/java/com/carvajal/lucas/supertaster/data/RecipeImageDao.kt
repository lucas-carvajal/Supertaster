package com.carvajal.lucas.supertaster.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecipeImageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRecipeImage(vararg recipeImage: RecipeImage)

    @Query("SELECT * FROM recipe_images WHERE recipe_id = :recipeId")
    fun getAllRecipeImages(recipeId: Int): List<RecipeImage>
}