package com.carvajal.lucas.supertaster.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carvajal.lucas.supertaster.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class DashboardViewModel(private val repository: AppRepository) : ViewModel(), RecipeViewViewModel, RecipesListViewModel {

    // for RecipeViewViewModel
    override lateinit var viewRecipe: LiveData<Recipe>
    override lateinit var viewRecipeImages: LiveData<List<RecipeImage>>
    override lateinit var viewRecipeIngredients: LiveData<List<RecipeIngredient>>
    override lateinit var viewRecipeSteps: LiveData<List<RecipeStep>>

    // for RecipesListViewModel
    override var listRecipes: LiveData<List<Recipe>> = repository.getAllRecipes()
    override var listRecipeImages: LiveData<List<RecipeImage>> = repository.getAllRecipeImages()
    override val allCookbooks: LiveData<List<Cookbook>> = repository.getAllCookbooks()

    override fun setRecipeId(recipeId: Long) {
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

    fun setListRecipesToAll() {
        listRecipes = repository.getAllRecipes()
    }

    fun filterListRecipesByName(name: String) {
        listRecipes = repository.filterRecipesByName(name)
    }

    fun filterRecipesByTime(time: Int) {
        listRecipes = repository.filterRecipesByTime(time)
    }

    fun filterRecipesByCuisine(cuisine: String) {
        listRecipes = repository.filterRecipesByCuisine(cuisine)
    }

    fun getGreeting() : String {
        //TODO
        return "GOOD MORNING"
    }

    fun getSuggestions(): LiveData<List<Recipe>> {
        val format = SimpleDateFormat("HH", Locale.US)
        val hour: Int = format.format(Date()).toInt()

        val suggestions = when(hour) {
            in 0..4 -> repository.getRecipeByType("Snack")
            in 5..9 -> repository.getRecipeByType("Breakfast")
            in 10..11 -> repository.getRecipeByType("Brunch")
            in 12..14 -> repository.getRecipeByType("Lunch")
            in 15..17 -> repository.getRecipeByType("Tea")
            in 18..20 -> repository.getRecipeByType("Dinner")
            in 21..24 -> repository.getRecipeByType("Snack")
            else -> repository.getRecipeByType("Other")
        }



        //TODO
        return suggestions
    }

    override fun addRecipeToCookbook(cookbookId: Long, recipeId: Long) {
        val newCookbookRecipe = CookbookRecipe(0, cookbookId, recipeId)
        viewModelScope.launch(Dispatchers.IO) {
            repository.addCookbookRecipe(newCookbookRecipe)
        }
    }

}