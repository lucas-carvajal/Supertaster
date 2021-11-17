package com.carvajal.lucas.supertaster.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.carvajal.lucas.supertaster.data.AppRepository
import com.carvajal.lucas.supertaster.data.Recipe

class AddViewViewModel(
    private val repository: AppRepository,
    private val recipeId: Long
): ViewModel() {

    val recipe: LiveData<Recipe> = repository.getRecipe(recipeId)

}