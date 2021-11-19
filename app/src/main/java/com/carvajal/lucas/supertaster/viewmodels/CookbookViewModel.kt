package com.carvajal.lucas.supertaster.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.carvajal.lucas.supertaster.data.*

class CookbookViewModel(private val repository: AppRepository) : ViewModel(), AddViewViewModel {

    override lateinit var viewRecipe: LiveData<Recipe>
    override lateinit var viewRecipeImages: LiveData<List<RecipeImage>>
    override lateinit var viewRecipeIngredients: LiveData<List<RecipeIngredient>>
    override lateinit var viewRecipeSteps: LiveData<List<RecipeStep>>

    fun setRecipeId(recipeId: Long) {
        viewRecipe = repository.getRecipe(recipeId)
        viewRecipeImages = repository.getRecipeImages(recipeId)
        viewRecipeIngredients = repository.getAllRecipeIngredients(recipeId)
        viewRecipeSteps = repository.getAllRecipeSteps(recipeId)
    }

    fun getCookbooks(): List<Cookbook> {
        //TODO
//        return emptyList()
        return listOf(
            Cookbook(0, "Burgers"),
            Cookbook(1, "Thai"),
            Cookbook(2, "Soups"),
            Cookbook(3, "BBQ"),
            Cookbook(4, "French"),
            Cookbook(5, "One Pot"),
            Cookbook(6, "Mediterranean"),
            Cookbook(7, "Steak"),
            Cookbook(8, "Noodles"),
            Cookbook(9, "Pastries"),
            Cookbook(9, "Sushi")
        )
    }

}