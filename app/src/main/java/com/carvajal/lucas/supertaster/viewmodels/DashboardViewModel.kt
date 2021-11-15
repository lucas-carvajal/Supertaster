package com.carvajal.lucas.supertaster.viewmodels

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.media.Image
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carvajal.lucas.supertaster.data.AppRepository
import com.carvajal.lucas.supertaster.data.Recipe
import com.carvajal.lucas.supertaster.data.RecipeImage
import kotlinx.coroutines.launch

class DashboardViewModel(private val repository: AppRepository) : ViewModel() {

    private val sampleRecipesData: LiveData<List<Recipe>> = repository.getRecipeSamples()
    val sampleRecipes = Transformations.map(sampleRecipesData) {
        it.take(10)
    }

    private val recipeImagesData: LiveData<List<RecipeImage>> = repository.getAllRecipeImages()
    val recipeImages = Transformations.map(recipeImagesData) { images ->
        images
        //images.filter {}
    }


    fun getGreeting() : String {
        //TODO
        return "GOOD MORNING"
    }

    fun getSuggestions(): List<Recipe> {
        //TODO
        return listOf()
    }
}