package com.carvajal.lucas.supertaster.viewmodels

import androidx.lifecycle.*
import com.carvajal.lucas.supertaster.data.*
import kotlinx.coroutines.*

class CookbookViewModel(private val repository: AppRepository) : ViewModel(), RecipeViewViewModel, RecipesListViewModel {

    override val allCookbooks = repository.getAllCookbooks()

    // for RecipeViewViewModel
    override lateinit var viewRecipe: LiveData<Recipe>
    override lateinit var viewRecipeImages: LiveData<List<RecipeImage>>
    override lateinit var viewRecipeIngredients: LiveData<List<RecipeIngredient>>
    override lateinit var viewRecipeSteps: LiveData<List<RecipeStep>>

    private var currentCookbookId: Long = -1

    var currentCookbook: LiveData<Cookbook> = repository.getCookbook(currentCookbookId)
    private var recipesInCookbookRaw: LiveData<List<CookbookRecipe>> = repository
        .getAllCookbookRecipes(currentCookbookId)
    val recipesInCookbook = Transformations.map(recipesInCookbookRaw) { list ->
        list.map { it.recipeId }
    }

    fun getCurrentCoookbook(): LiveData<Cookbook> {
        return repository.getCookbook(currentCookbookId)
    }

    // for RecipesListViewModel
    override var listRecipes: LiveData<List<Recipe>> = repository.getAllRecipes()
    override var listRecipeImages: LiveData<List<RecipeImage>> = repository.getAllRecipeImages()

    fun setCurrentCookbook(id: Long) {
        currentCookbookId = id
    }

    fun addCookbook(name: String) {
        val newCookbook = Cookbook(0, name)
        viewModelScope.launch(Dispatchers.IO) {
            repository.addCookbook(newCookbook)
        }
    }

    fun setRecipeId(recipeId: Long) {
        viewRecipe = repository.getRecipe(recipeId)
        viewRecipeImages = repository.getRecipeImages(recipeId)
        viewRecipeIngredients = repository.getAllRecipeIngredients(recipeId)
        viewRecipeSteps = repository.getAllRecipeSteps(recipeId)
    }

    override fun addRecipeToCookbook(cookbookId: Long, recipeId: Long) {
        val newCookbookRecipe = CookbookRecipe(0, cookbookId, recipeId)
        viewModelScope.launch(Dispatchers.IO) {
            repository.addCookbookRecipe(newCookbookRecipe)
        }
    }

}