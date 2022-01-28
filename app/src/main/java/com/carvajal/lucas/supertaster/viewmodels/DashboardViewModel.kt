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

class DashboardViewModel(private val repository: AppRepository) : ViewModel(), RecipeViewListViewModel {

    override var listTitle: String? = null

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

    fun filterRecipesByIngredients(ingredient: String) {
        val targetRecipes = repository.getRecipeIdsByIngredient(ingredient)

        listRecipes = Transformations.switchMap(targetRecipes) {
            if(it.isNullOrEmpty()) {
                repository.getRecipes(listOf(-1))
            } else {
                repository.getRecipes(it)
            }
        }
    }

    fun filterRecipesByCuisine(cuisine: String) {
        listRecipes = repository.filterRecipesByCuisine(cuisine)
    }

    fun getGreeting() : String {
        val format = SimpleDateFormat("HH", Locale.US)
        val hour: Int = format.format(Date()).toInt()

        return when(hour) {
            in 0..3 -> "GOOD NIGHT"
            in 4..12 -> "GOOD MORNING"
            in 13..17 -> "GOOD AFTERNOON"
            in 18..24 -> "GOOD EVENING"
            else -> "HELLO!"
        }
    }

    fun getSuggestions(): LiveData<List<Recipe>> {
        val format = SimpleDateFormat("HH", Locale.US)
        val hour: Int = format.format(Date()).toInt()

        val suggestions = when(hour) {
            in 0..4 -> repository.getRecipeByType(6) // Snack
            in 5..9 -> repository.getRecipeByType(1) // Breakfast
            in 10..11 -> repository.getRecipeByType(2) // Brunch
            in 12..14 -> repository.getRecipeByType(3) // Lunch
            in 15..17 -> repository.getRecipeByType(4) // Tea
            in 18..20 -> repository.getRecipeByType(5) // Dinner
            in 21..24 -> repository.getRecipeByType(6) // Snack
            else -> repository.getRecipeByType(8) // Other
        }

        return suggestions
    }

    override fun addRecipeToCookbook(cookbookId: Long, recipeId: Long) {
        val newCookbookRecipe = CookbookRecipe(0, cookbookId, recipeId)
        viewModelScope.launch(Dispatchers.IO) {
            repository.addCookbookRecipe(newCookbookRecipe)
        }
    }

}