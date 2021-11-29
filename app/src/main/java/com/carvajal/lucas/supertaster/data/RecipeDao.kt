package com.carvajal.lucas.supertaster.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRecipe(recipe: Recipe): Long

    @Query("SELECT * FROM recipes")
    fun getAllRecipes(): LiveData<List<Recipe>>

    @Query("SELECT * FROM recipes LIMIT 5")
    fun getRecipeSamples(): LiveData<List<Recipe>>

    @Query("SELECT * FROM recipes WHERE type_of_meal = :typeOfMeal LIMIT 5")
    fun getRecipeByType(typeOfMeal: String): LiveData<List<Recipe>>

    @Query("SELECT * FROM recipes WHERE id = :id LIMIT 1")
    fun getRecipe(id: Long): LiveData<Recipe>
}