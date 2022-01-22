package com.carvajal.lucas.supertaster.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecipeImageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRecipeImage(recipeImage: RecipeImage): Long

    @Query("SELECT * FROM recipe_images")
    fun getAllRecipeImages(): LiveData<List<RecipeImage>>

    @Query("SELECT * FROM recipe_images WHERE recipe_id = :recipeId")
    fun getRecipeImages(recipeId: Long): LiveData<List<RecipeImage>>

    @Query("SELECT * FROM recipe_images WHERE recipe_id = :recipeId")
    fun getRecipeImagesStatic(recipeId: Long): List<RecipeImage>
}