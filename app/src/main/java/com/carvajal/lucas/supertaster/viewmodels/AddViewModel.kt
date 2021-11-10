package com.carvajal.lucas.supertaster.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.carvajal.lucas.supertaster.R
import com.carvajal.lucas.supertaster.data.AppDatabase
import com.carvajal.lucas.supertaster.data.AppRepository

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



        return true
    }


    private fun initRepository(context: Context) {
        val recipeDao = AppDatabase.getDatabase(context).recipeDao()
        val recipeImageDao = AppDatabase.getDatabase(context).recipeImageDao()
        val recipeIngredientDao = AppDatabase.getDatabase(context).recipeIngredientDao()
        val recipeStepDao = AppDatabase.getDatabase(context).recipeStepDao()
        val cookbookDao = AppDatabase.getDatabase(context).cookbookDao()
        val cookbookRecipeDao = AppDatabase.getDatabase(context).cookbookRecipeDao()

        appRepository = AppRepository(
            recipeDao,
            recipeImageDao,
            recipeIngredientDao,
            recipeStepDao,
            cookbookDao,
            cookbookRecipeDao
        )
    }
}