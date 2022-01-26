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

    @Query("SELECT * FROM recipes WHERE title LIKE '%' || :name || '%'")
    fun filterRecipesByName(name: String) : LiveData<List<Recipe>>

    @Query("SELECT * FROM recipes WHERE (prep_time + cook_time) <= :time")
    fun filterRecipesByTime(time: Int) : LiveData<List<Recipe>>

    @Query("SELECT * FROM recipes WHERE cuisine LIKE :cuisine")
    fun filterRecipesByCuisine(cuisine: String) : LiveData<List<Recipe>>

    @Query("SELECT * FROM recipes WHERE type_of_meal_index = :typeOfMealIndex LIMIT 5")
    fun getRecipeByType(typeOfMealIndex: Int): LiveData<List<Recipe>>

    @Query("SELECT * FROM recipes WHERE id = :id LIMIT 1")
    fun getRecipe(id: Long): LiveData<Recipe>

    @Query("SELECT * FROM recipes WHERE id = :id")
    fun getRecipes(vararg id: Long): LiveData<List<Recipe>>

    @Query("SELECT * FROM recipes WHERE id = :id LIMIT 1")
    fun getRecipeStatic(id: Long): Recipe

    @Query("DELETE FROM recipes WHERE id = :id")
    fun deleteRecipeById(id: Long)
}