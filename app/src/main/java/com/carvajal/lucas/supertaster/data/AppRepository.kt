package com.carvajal.lucas.supertaster.data

import androidx.lifecycle.LiveData
import androidx.room.Query


class AppRepository(
    private val recipeDao: RecipeDao,
    private val recipeImageDao: RecipeImageDao,
    private val recipeIngredientDao: RecipeIngredientDao,
    private val recipeStepDao: RecipeStepDao,
    private val cookbookDao: CookbookDao,
    private val cookbookRecipeDao: CookbookRecipeDao
    ) {

    // Recipe
    fun getAllRecipes(): LiveData<List<Recipe>> {
        return recipeDao.getAllRecipes()
    }

    fun getRecipeSamples(): LiveData<List<Recipe>> {
        return recipeDao.getRecipeSamples()
    }

    fun filterRecipesByName(name: String) : LiveData<List<Recipe>> {
        return recipeDao.filterRecipesByName(name)
    }

    fun filterRecipesByTime(time: Int) : LiveData<List<Recipe>>{
        return recipeDao.filterRecipesByTime(time)
    }

    fun filterRecipesByCuisine(cuisine: String) : LiveData<List<Recipe>> {
        return recipeDao.filterRecipesByCuisine(cuisine)
    }

    fun getRecipeByType(typeOfMeal: Int): LiveData<List<Recipe>> {
        return recipeDao.getRecipeByType(typeOfMeal)
    }

    fun getRecipe(id: Long): LiveData<Recipe> {
        return recipeDao.getRecipe(id)
    }

    fun getRecipeStatic(id: Long): Recipe {
        return recipeDao.getRecipeStatic(id)
    }

    fun addRecipe(recipe: Recipe): Long {
        return recipeDao.addRecipe(recipe)
    }


    // RecipeImage
    fun getAllRecipeImages(): LiveData<List<RecipeImage>> {
        return recipeImageDao.getAllRecipeImages()
    }

    fun getRecipeImages(recipeId: Long): LiveData<List<RecipeImage>> {
        return recipeImageDao.getRecipeImages(recipeId)
    }


    fun addRecipeImage(recipeImage: RecipeImage): Long {
        return recipeImageDao.addRecipeImage(recipeImage)
    }


    // RecipeIngredient
    fun getAllRecipeIngredients(id: Long): LiveData<List<RecipeIngredient>> {
        return recipeIngredientDao.getAllRecipeIngredients(id)
    }

    fun addRecipeIngredient(recipeIngredient: RecipeIngredient): Long {
        return recipeIngredientDao.addRecipeIngredient(recipeIngredient)
    }


    // RecipeStep
    fun getAllRecipeSteps(id: Long): LiveData<List<RecipeStep>> {
        return recipeStepDao.getAllRecipeSteps(id)
    }

    fun addRecipeStep(recipeStep: RecipeStep): Long {
        return recipeStepDao.addRecipeStep(recipeStep)
    }


    // Cookbook
    fun getAllCookbooks(): LiveData<List<Cookbook>> {
        return cookbookDao.getAllCookbooks()
    }

    fun getCookbook(id: Long): LiveData<Cookbook> {
        return cookbookDao.getCookbook(id)
    }

    fun addCookbook(cookbook: Cookbook): Long {
        return cookbookDao.addCookbook(cookbook)
    }


    // CookbookRecipe
    fun getAllCookbookRecipes(cookbookId: Long): LiveData<List<CookbookRecipe>> {
        return cookbookRecipeDao.getAllCookbookRecipes(cookbookId)
    }

    fun addCookbookRecipe(cookbookRecipe: CookbookRecipe): Long {
        return cookbookRecipeDao.addCookbookRecipe(cookbookRecipe)
    }

}