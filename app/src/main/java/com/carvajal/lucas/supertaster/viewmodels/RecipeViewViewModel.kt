package com.carvajal.lucas.supertaster.viewmodels

import androidx.lifecycle.LiveData
import com.carvajal.lucas.supertaster.data.*

interface RecipeViewViewModel {

    var viewRecipe: LiveData<Recipe>
    var viewRecipeImages: LiveData<List<RecipeImage>>
    var viewRecipeIngredients: LiveData<List<RecipeIngredient>>
    var viewRecipeSteps: LiveData<List<RecipeStep>>
    val allCookbooks: LiveData<List<Cookbook>>

    fun addRecipeToCookbook(cookbookId: Long, recipeId: Long)

}