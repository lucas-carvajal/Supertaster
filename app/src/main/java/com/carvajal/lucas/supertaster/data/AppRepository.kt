package com.carvajal.lucas.supertaster.data


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

    fun addRecipe(recipe: Recipe): Long {
        return recipeDao.addRecipe(recipe)
    }


    // RecipeImage
    fun getAllRecipeImages(id: Int): List<RecipeImage> {
        return recipeImageDao.getAllRecipeImages(id)
    }

    fun addRecipes(recipeImage: RecipeImage): Long {
        return recipeImageDao.addRecipeImage(recipeImage)
    }


    // RecipeIngredient
    fun getAllRecipeIngredients(id: Int): List<RecipeIngredient> {
        return recipeIngredientDao.getAllRecipeIngredients(id)
    }

    fun addRecipeIngredient(recipeIngredient: RecipeIngredient): Long {
        return recipeIngredientDao.addRecipeIngredient(recipeIngredient)
    }


    // RecipeStep
    fun getAllRecipeSteps(id: Int): List<RecipeStep> {
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
    fun getAllCookbookRecipes(cookbookId: Int): List<CookbookRecipe> {
        return cookbookRecipeDao.getAllCookbookRecipes(cookbookId)
    }

    fun addCookbookRecipe(cookbookRecipe: CookbookRecipe): Long {
        return cookbookRecipeDao.addCookbookRecipe(cookbookRecipe)
    }

}