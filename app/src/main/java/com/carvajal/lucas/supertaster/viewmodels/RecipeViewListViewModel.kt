package com.carvajal.lucas.supertaster.viewmodels

import androidx.lifecycle.LiveData
import com.carvajal.lucas.supertaster.data.*

interface RecipeViewListViewModel {

    var viewRecipe: LiveData<Recipe>
    var viewRecipeImages: LiveData<List<RecipeImage>>
    var viewRecipeIngredients: LiveData<List<RecipeIngredient>>
    var viewRecipeSteps: LiveData<List<RecipeStep>>
    val allCookbooks: LiveData<List<Cookbook>>

    var listTitle: String?

    val listRecipeImages: LiveData<List<RecipeImage>>
    val listRecipes: LiveData<List<Recipe>>

    fun setRecipeId(recipeId: Long)

    fun addRecipeToCookbook(cookbookId: Long, recipeId: Long)

}