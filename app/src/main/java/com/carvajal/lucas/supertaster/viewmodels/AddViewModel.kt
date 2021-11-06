package com.carvajal.lucas.supertaster.viewmodels

import androidx.lifecycle.ViewModel
import com.carvajal.lucas.supertaster.R

class AddViewModel : ViewModel() {

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

}