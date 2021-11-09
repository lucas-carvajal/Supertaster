package com.carvajal.lucas.supertaster.viewmodels

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import com.carvajal.lucas.supertaster.R
import com.carvajal.lucas.supertaster.data.RecipeIngredient

class AddViewModel : ViewModel() {

    var ingredients: ArrayList<RecipeIngredient> = arrayListOf()

    fun getPhotos(): List<Int> {
        //TODO
        return listOf(
            R.drawable.tacos_al_pastor,
            R.drawable.tacos_al_pastor
        )
    }

    fun getMealTypes(): List<String> {
        return listOf("-", "Breakfast", "Lunch", "Dinner", "Snack")
    }

//    fun getIngredientsList(): List<RecipeIngredient> {
//        return ingredients
//    }

    fun addIngredient(newIngredient: RecipeIngredient) {
        ingredients.add(newIngredient)
    }

    fun changeIngredient(newIngredient: RecipeIngredient, index: Int) {
        ingredients[index] = newIngredient
    }

}