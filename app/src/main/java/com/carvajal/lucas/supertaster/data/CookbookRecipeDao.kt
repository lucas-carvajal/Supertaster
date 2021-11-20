package com.carvajal.lucas.supertaster.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CookbookRecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCookbookRecipe(cookbookRecipe: CookbookRecipe): Long

    @Query("SELECT * FROM cookbook_recipes WHERE cookbook_id = :cookbookId")
    fun getAllCookbookRecipes(cookbookId: Long): LiveData<List<CookbookRecipe>>
}