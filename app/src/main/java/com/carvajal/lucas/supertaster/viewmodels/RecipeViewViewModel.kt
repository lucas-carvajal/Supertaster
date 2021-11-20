package com.carvajal.lucas.supertaster.viewmodels

import androidx.lifecycle.LiveData
import com.carvajal.lucas.supertaster.data.Recipe
import com.carvajal.lucas.supertaster.data.RecipeImage
import com.carvajal.lucas.supertaster.data.RecipeIngredient
import com.carvajal.lucas.supertaster.data.RecipeStep

interface RecipeViewViewModel {

    var viewRecipe: LiveData<Recipe>
    var viewRecipeImages: LiveData<List<RecipeImage>>
    var viewRecipeIngredients: LiveData<List<RecipeIngredient>>
    var viewRecipeSteps: LiveData<List<RecipeStep>>

}