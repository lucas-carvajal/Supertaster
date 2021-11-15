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
}