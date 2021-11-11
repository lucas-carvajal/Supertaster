package com.carvajal.lucas.supertaster.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.carvajal.lucas.supertaster.R
import com.carvajal.lucas.supertaster.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddViewModel : ViewModel() {

    private var appRepository: AppRepository? = null

    var title: String = ""
    var cuisine: String = ""
    var typeOfMealIndex: Int = 0
    var servings: Int = 2
    var prepTime: Int = 5
    var cookTime: Int = 5

    private var recipePhotos: MutableList<String> = mutableListOf() //TODO
    private var ingredients: MutableList<Pair<String, String>> = mutableListOf()
    private var steps: MutableList<Triple<Int, String, String>> = mutableListOf()

    fun getPhotos(): List<Int> {
        //TODO
        return listOf(
            R.drawable.tacos_al_pastor,
        )
    }

    fun getMealTypes(): List<String> {
        return listOf("-", "Breakfast", "Lunch", "Dinner", "Snack")
    }

    fun getIngredients(): MutableList<Pair<String, String>> {
        return ingredients
    }

    fun setIngredients(newIngredientsList: MutableList<Pair<String, String>>) {
        ingredients = newIngredientsList
    }

    fun getSteps(): MutableList<Triple<Int, String, String>> {
        return steps
    }

    fun setSteps(newStepsList: MutableList<Triple<Int, String, String>>) {
        steps = newStepsList
    }

    fun saveRecipe(context: Context): Boolean {
        if (appRepository == null) {
            initRepository(context)
        }

        //TODO try catch ?

        val mealTypes = getMealTypes()

        viewModelScope.launch(Dispatchers.IO) {
            val recipeId = appRepository?.addRecipe(
                Recipe(
                    0,
                    title,
                    cuisine,
                    mealTypes[typeOfMealIndex],
                    servings,
                    prepTime,
                    cookTime
                )
            )

            recipePhotos.forEach { photo ->
                appRepository?.addRecipeImage(RecipeImage(recipeId!!, photo))
            }

            ingredients.forEach{ ingredient ->
                appRepository?.addRecipeIngredient(RecipeIngredient(recipeId!!, ingredient.first, ingredient.second))
            }

            steps.forEach{ step ->
                appRepository?.addRecipeStep(RecipeStep(recipeId!!, step.first, step.second, step.third))
            }
        }

        return true
    }


    private fun initRepository(context: Context) {
        val recipeDao = AppDatabase.getInstance(context).recipeDao()
        val recipeImageDao = AppDatabase.getInstance(context).recipeImageDao()
        val recipeIngredientDao = AppDatabase.getInstance(context).recipeIngredientDao()
        val recipeStepDao = AppDatabase.getInstance(context).recipeStepDao()
        val cookbookDao = AppDatabase.getInstance(context).cookbookDao()
        val cookbookRecipeDao = AppDatabase.getInstance(context).cookbookRecipeDao()

        appRepository = AppRepository(
            recipeDao,
            recipeImageDao,
            recipeIngredientDao,
            recipeStepDao,
            cookbookDao,
            cookbookRecipeDao
        )

//        val db = Room.databaseBuilder(context, AppDatabase::class.java, "app_database").build()
    }
}