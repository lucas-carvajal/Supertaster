package com.carvajal.lucas.supertaster.data

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
    fun getAllRecipes(): List<Recipe> {
        return recipeDao.getAllRecipes()
    }

    fun addRecipe(recipe: Recipe) {
        recipeDao.addRecipe(recipe)
    }


    // RecipeImage
    fun getAllRecipeImages(id: Int): List<RecipeImage> {
        return recipeImageDao.getAllRecipeImages(id)
    }

    fun addRecipes(recipeImage: RecipeImage) {
        recipeImageDao.addRecipeImage(recipeImage)
    }


    // RecipeIngredient
    fun getAllRecipeIngredients(id: Int): List<RecipeIngredient> {
        return recipeIngredientDao.getAllRecipeIngredients(id)
    }

    fun addRecipeIngredient(recipeIngredient: RecipeIngredient) {
        recipeIngredientDao.addRecipeIngredient(recipeIngredient)
    }


    // RecipeStep
    fun getAllRecipeSteps(id: Int): List<RecipeStep> {
        return recipeStepDao.getAllRecipeSteps(id)
    }

    fun addRecipeStep(recipeStep: RecipeStep) {
        recipeStepDao.addRecipeStep(recipeStep)
    }


    // Cookbook
    fun addCookbook(cookbook: Cookbook) {
        cookbookDao.addCookbook(cookbook)
    }
    fun getAllRecipeIngredients(): List<Cookbook> {
        return cookbookDao.getAllCookbooks()
    }


    // CookbookRecipe
    fun addCookbookRecipe(cookbookRecipe: CookbookRecipe) {
        cookbookRecipeDao.addCookbookRecipe(cookbookRecipe)
    }

    fun getAllCookbookRecipes(cookbookId: Int): List<CookbookRecipe> {
        return cookbookRecipeDao.getAllCookbookRecipes(cookbookId)
    }
}