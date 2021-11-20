package com.carvajal.lucas.supertaster.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.carvajal.lucas.supertaster.data.*

class DashboardViewModel(private val repository: AppRepository) : ViewModel(), RecipeViewViewModel, RecipesListViewModel {

    // for RecipeViewViewModel
    override lateinit var viewRecipe: LiveData<Recipe>
    override lateinit var viewRecipeImages: LiveData<List<RecipeImage>>
    override lateinit var viewRecipeIngredients: LiveData<List<RecipeIngredient>>
    override lateinit var viewRecipeSteps: LiveData<List<RecipeStep>>

    // for RecipesListViewModel
    override var listRecipes: LiveData<List<Recipe>> = repository.getAllRecipes()
    override var listRecipeImages: LiveData<List<RecipeImage>> = repository.getAllRecipeImages()

    fun setRecipeId(recipeId: Long) {
        viewRecipe = repository.getRecipe(recipeId)
        viewRecipeImages = repository.getRecipeImages(recipeId)
        viewRecipeIngredients = repository.getAllRecipeIngredients(recipeId)
        viewRecipeSteps = repository.getAllRecipeSteps(recipeId)
    }

    private val sampleRecipesData: LiveData<List<Recipe>> = repository.getRecipeSamples()
    val sampleRecipes = Transformations.map(sampleRecipesData) {
        it.take(5)
    }

    private val recipeImagesData: LiveData<List<RecipeImage>> = repository.getAllRecipeImages()
    val recipeImages = Transformations.map(recipeImagesData) { images ->
        images
        //images.filter {}
    }

    fun getGreeting() : String {
        //TODO
        return "GOOD MORNING"
    }

    fun getSuggestions(): List<Recipe> {
        //TODO
        return listOf()
    }

}