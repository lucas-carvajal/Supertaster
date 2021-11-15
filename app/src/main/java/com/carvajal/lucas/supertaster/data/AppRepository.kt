package com.carvajal.lucas.supertaster.data

import androidx.lifecycle.LiveData


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

    fun addRecipe(recipe: Recipe): Long {
        return recipeDao.addRecipe(recipe)
    }


    // RecipeImage
    fun getAllRecipeImages(): LiveData<List<RecipeImage>> {
        return recipeImageDao.getAllRecipeImages()
    }


    fun addRecipeImage(recipeImage: RecipeImage): Long {
        return recipeImageDao.addRecipeImage(recipeImage)
    }


    // RecipeIngredient
    fun getAllRecipeIngredients(id: Long): List<RecipeIngredient> {
        return recipeIngredientDao.getAllRecipeIngredients(id)
    }

    fun addRecipeIngredient(recipeIngredient: RecipeIngredient): Long {
        return recipeIngredientDao.addRecipeIngredient(recipeIngredient)
    }


    // RecipeStep
    fun getAllRecipeSteps(id: Long): List<RecipeStep> {
        return recipeStepDao.getAllRecipeSteps(id)
    }

    fun addRecipeStep(recipeStep: RecipeStep): Long {
        return recipeStepDao.addRecipeStep(recipeStep)
    }


    // Cookbook
    fun getAllRecipeIngredients(): List<Cookbook> {
        return cookbookDao.getAllCookbooks()
    }

    fun addCookbook(cookbook: Cookbook): Long {
        return cookbookDao.addCookbook(cookbook)
    }


    // CookbookRecipe
    fun getAllCookbookRecipes(cookbookId: Long): List<CookbookRecipe> {
        return cookbookRecipeDao.getAllCookbookRecipes(cookbookId)
    }

    fun addCookbookRecipe(cookbookRecipe: CookbookRecipe): Long {
        return cookbookRecipeDao.addCookbookRecipe(cookbookRecipe)
    }

}