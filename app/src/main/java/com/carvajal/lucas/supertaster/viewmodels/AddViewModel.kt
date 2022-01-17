package com.carvajal.lucas.supertaster.viewmodels

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateListOf
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

data class Ingredient(val name: String, val amount: String)
data class Step(val sequence: Int, val description: String, val extraNote: String)

class AddViewModel(private val repository: AppRepository) : ViewModel() {

    var title: String = ""
    var cuisine: String = ""
    var typeOfMealIndex: Int = 0
    var servings: Int = 2
    var prepTime: Int = 5
    var cookTime: Int = 5

    val recipePhotos = mutableStateListOf<Bitmap>()
    val ingredients = mutableStateListOf<Ingredient>()
    val steps = mutableStateListOf<Step>()

    fun addRecipePhotos(bitmap: Bitmap) {
        recipePhotos.add(bitmap)
    }

    fun removeRecipePhoto(index: Int) {
        recipePhotos.removeAt(index)
    }

    private fun clearRecipePhotos() {
        recipePhotos.clear()
    }

    fun addIngredient(ingredient: Ingredient) {
        ingredients.add(ingredient)
    }

    fun updateIngredient(index: Int, ingredient: Ingredient) {
        ingredients[index] = ingredient
    }

    private fun clearIngredients() {
        ingredients.clear()
    }

    fun addStep(step: Step) {
        steps.add(step)
    }

    fun updateSteps(index: Int, step: Step) {
        steps[index] = step
    }

    private fun clearSteps() {
        steps.clear()
    }


    fun getMealTypes(): List<String> {
        return listOf("-", "Breakfast", "Brunch", "Lunch", "Tea", "Dinner", "Snack", "Drink", "Other")
    }

    fun loadData(recipeId: Long) {
        val recipe = repository.getRecipe(recipeId).value

        title = recipe?.title ?: ""
        cuisine = recipe?.cuisine ?: ""
        //TODO cast typeOfMeal to Int
        //typeOfMealIndex = recipe.value?.typeOfMeal ?: 0
        servings = recipe?.servings ?: 0
        prepTime = recipe?.prepTime ?: 0
        cookTime = recipe?.cookTime ?: 0

        repository.getAllRecipeIngredients(recipeId).value?.map {
            Ingredient(it.ingredient, it.amount)
        }?.forEach {
            ingredients.add(it)
        }


        //TODO
        //recipePhotos = repository.getRecipeImages(recipeId)
        //steps = mutableStateListOf<Step>()
    }


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

        val savedRecipePhotos = recipePhotos.toList()
        val savedIngredients = ingredients.toList()
        val savedSteps = steps.toList()

        // cleanup vars for new recipe
        title = ""
        cuisine = ""
        typeOfMealIndex = 0
        servings = 2
        prepTime = 5
        cookTime = 5
        clearRecipePhotos()
        clearIngredients()
        clearSteps()

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
                repository.addRecipeIngredient(RecipeIngredient(UniqueIdGenerator.generateLongId(), recipeId, ingredient.name, ingredient.amount))
            }

            savedSteps.forEach{ step ->
                repository.addRecipeStep(RecipeStep(UniqueIdGenerator.generateLongId(), recipeId, step.sequence, step.description, step.extraNote))
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