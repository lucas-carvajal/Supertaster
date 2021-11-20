package com.carvajal.lucas.supertaster.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.carvajal.lucas.supertaster.data.*

class CookbookViewModel(private val repository: AppRepository) : ViewModel(), RecipeViewViewModel, RecipesListViewModel {

    // for RecipeViewViewModel
    override lateinit var viewRecipe: LiveData<Recipe>
    override lateinit var viewRecipeImages: LiveData<List<RecipeImage>>
    override lateinit var viewRecipeIngredients: LiveData<List<RecipeIngredient>>
    override lateinit var viewRecipeSteps: LiveData<List<RecipeStep>>

    lateinit var currentCookbook: LiveData<Cookbook>
    private var recipesInCookbookRaw: LiveData<List<CookbookRecipe>> = repository.getAllCookbookRecipes(-1)
    val recipesInCookbook = Transformations.map(recipesInCookbookRaw) { list ->
        list.map { it.recipeId }
    }

    // for RecipesListViewModel
    override var listRecipes: LiveData<List<Recipe>> = repository.getAllRecipes()
    override var listRecipeImages: LiveData<List<RecipeImage>> = repository.getAllRecipeImages()

    fun getCookbookTitle(): String {
        return currentCookbook.value?.name ?: "Error"
    }

    fun setCurrentCookbook(id: Long) {
        currentCookbook = repository.getCookbook(id)
        recipesInCookbookRaw = repository.getAllCookbookRecipes(repository.getCookbook(id).value?.id ?: -1)
    }

    fun setRecipeId(recipeId: Long) {
        viewRecipe = repository.getRecipe(recipeId)
        viewRecipeImages = repository.getRecipeImages(recipeId)
        viewRecipeIngredients = repository.getAllRecipeIngredients(recipeId)
        viewRecipeSteps = repository.getAllRecipeSteps(recipeId)
    }

    fun getCookbooks(): List<Cookbook> {
//        return repository.getAllCookbooks()
        //TODO
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