package com.carvajal.lucas.supertaster.data

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

interface CookbookRecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCookbookRecipe(vararg cookbookRecipe: CookbookRecipe)

    @Query("SELECT * FROM cookbook_recipes WHERE cookbook_id = :cookbookId")
    fun getAllCookbookRecipes(cookbookId: Int): List<CookbookRecipe>
}