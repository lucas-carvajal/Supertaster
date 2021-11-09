package com.carvajal.lucas.supertaster.viewmodels

import androidx.lifecycle.ViewModel
import com.carvajal.lucas.supertaster.R

class AddViewModel : ViewModel() {

    private var ingredients: MutableList<Pair<String, String>> = mutableListOf()

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

    fun getIngredients(): MutableList<Pair<String, String>> {
        return ingredients
    }

    fun setIngredients(newIngredientsList: MutableList<Pair<String, String>>) {
        ingredients = newIngredientsList
    }
}