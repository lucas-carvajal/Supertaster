package com.carvajal.lucas.supertaster.viewmodels

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.carvajal.lucas.supertaster.R
import com.carvajal.lucas.supertaster.data.*
import com.carvajal.lucas.supertaster.util.UniqueIdGenerator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class AddViewModel : ViewModel() {

    private var appRepository: AppRepository? = null

    var title: String = ""
    var cuisine: String = ""
    var typeOfMealIndex: Int = 0
    var servings: Int = 2
    var prepTime: Int = 5
    var cookTime: Int = 5

    private var recipePhotos: MutableList<Bitmap> = mutableListOf() //TODO
    private var ingredients: MutableList<Pair<String, String>> = mutableListOf()
    private var steps: MutableList<Triple<Int, String, String>> = mutableListOf()

    fun getPhotos(): List<Bitmap> {
        //TODO
        //return listOf(R.drawable.tacos_al_pastor)
        return recipePhotos
    }

    fun addPhoto(image: Bitmap) {
        recipePhotos.add(image)
    }

    fun deletePhoto(index: Int) {
        recipePhotos.removeAt(index)
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
                    UniqueIdGenerator.generateLongId(),
                    title,
                    cuisine,
                    mealTypes[typeOfMealIndex],
                    servings,
                    prepTime,
                    cookTime
                )
            )

            recipePhotos.forEach { photo ->
                val photoLocation = savePhoto(photo, title, context)
                appRepository?.addRecipeImage(RecipeImage(UniqueIdGenerator.generateLongId(), recipeId!!, photoLocation))
            }

            ingredients.forEach{ ingredient ->
                appRepository?.addRecipeIngredient(RecipeIngredient(UniqueIdGenerator.generateLongId(), recipeId!!, ingredient.first, ingredient.second))
            }

            steps.forEach{ step ->
                appRepository?.addRecipeStep(RecipeStep(UniqueIdGenerator.generateLongId(), recipeId!!, step.first, step.second, step.third))
            }
        }

        return true
    }

    @Throws(IOException::class)
    private fun savePhoto(image: Bitmap, recipeTitle: String, context: Context): String {
        var photoPath: String = ""

        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmssSSS").format(Date())
        val storageDir: File = context.filesDir
        val trimmedRecipeTitle = recipeTitle.filter { !it.isWhitespace() }
        val file = File.createTempFile(
            "JPEG_${timeStamp}_${trimmedRecipeTitle}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            photoPath = absolutePath
        }

        return photoPath
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
    }
}