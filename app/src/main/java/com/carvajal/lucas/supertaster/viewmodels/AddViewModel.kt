package com.carvajal.lucas.supertaster.viewmodels

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carvajal.lucas.supertaster.data.*
import com.carvajal.lucas.supertaster.util.UniqueIdGenerator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class AddViewModel(private val repository: AppRepository) : ViewModel() {

    var title: String = ""
    var cuisine: String = ""
    var typeOfMealIndex: Int = 0
    var servings: Int = 2
    var prepTime: Int = 5
    var cookTime: Int = 5

    var recipePhotos: MutableList<Bitmap> = mutableListOf() //TODO
    var ingredients: MutableList<Pair<String, String>> = mutableListOf()
    var steps: MutableList<Triple<Int, String, String>> = mutableListOf()

//    fun getPhotos(): List<Bitmap> {
//        //TODO
//        //return listOf(R.drawable.tacos_al_pastor)
//        return recipePhotos
//    }

    fun addPhoto(image: Bitmap) {
        recipePhotos.add(image)
    }

    fun deletePhoto(index: Int) {
        recipePhotos.removeAt(index)
    }

    fun getMealTypes(): List<String> {
        return listOf("-", "Breakfast", "Brunch", "Lunch", "Tea", "Dinner", "Snack", "Drink", "Other")
    }

//    fun getIngredients(): MutableList<Pair<String, String>> {
//        return ingredients
//    }

//    fun setIngredients(newIngredientsList: MutableList<Pair<String, String>>) {
//        ingredients = newIngredientsList
//    }

//    fun getSteps(): MutableList<Triple<Int, String, String>> {
//        return steps
//    }
//
//    fun setSteps(newStepsList: MutableList<Triple<Int, String, String>>) {
//        steps = newStepsList
//    }

    fun saveRecipe(context: Context): Boolean {
        //TODO try catch ?
        val mealTypes = getMealTypes()

        // save all variables
        val savedTitle = title
        val savedCuisine = cuisine
        val savedTypeOfMealIndex = typeOfMealIndex
        val savedServings = servings
        val savedPrepTime = prepTime
        val savedCookTime = cookTime

        val savedRecipePhotos = recipePhotos
        val savedIngredients = ingredients
        val savedSteps = steps

        // cleanup vars for new recipe
        title = ""
        cuisine = ""
        typeOfMealIndex = 0
        servings = 2
        prepTime = 5
        cookTime = 5
        recipePhotos = recipePhotos.toMutableList().apply { clear() }
        ingredients = ingredients.toMutableList().apply { clear() }
        steps = steps.toMutableList().apply { clear() }

        println("RECIPE PHOTOS: $recipePhotos")
        println("I N G R E D I E N T S: $ingredients")
        println("STEPS: $steps")

        // submit recipe
        viewModelScope.launch(Dispatchers.IO) {
            val recipeId = repository.addRecipe(
                Recipe(
                    UniqueIdGenerator.generateLongId(),
                    savedTitle,
                    savedCuisine,
                    mealTypes[savedTypeOfMealIndex],
                    savedServings,
                    savedPrepTime,
                    savedCookTime
                )
            )

            savedRecipePhotos.forEach { photo ->
                val photoLocation = savePhoto(photo, savedTitle, context)
                repository.addRecipeImage(RecipeImage(UniqueIdGenerator.generateLongId(), recipeId, photoLocation))
            }

            savedIngredients.forEach{ ingredient ->
                repository.addRecipeIngredient(RecipeIngredient(UniqueIdGenerator.generateLongId(), recipeId, ingredient.first, ingredient.second))
            }

            savedSteps.forEach{ step ->
                repository.addRecipeStep(RecipeStep(UniqueIdGenerator.generateLongId(), recipeId, step.first, step.second, step.third))
            }
        }
        return true
    }

    @Throws(IOException::class)
    private fun savePhoto(image: Bitmap, recipeTitle: String, context: Context): String {
        var photoPath: String

        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmssSSS").format(Date())
        val storageDir: File = context.filesDir
        val trimmedRecipeTitle = recipeTitle.filter { !it.isWhitespace() }
        var file = File.createTempFile(
            "JPEG_${timeStamp}_${trimmedRecipeTitle}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            photoPath = absolutePath
        }

        val out = FileOutputStream(file)
        image.compress(Bitmap.CompressFormat.JPEG, 100, out)
        out.flush()
        out.close()

        return photoPath
    }
}