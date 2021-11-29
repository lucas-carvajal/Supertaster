package com.carvajal.lucas.supertaster.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.carvajal.lucas.supertaster.data.Recipe
import com.carvajal.lucas.supertaster.data.RecipeImage

interface RecipesListViewModel {

    val listRecipeImages: LiveData<List<RecipeImage>>
    val listRecipes: LiveData<List<Recipe>>

}