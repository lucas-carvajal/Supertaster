package com.carvajal.lucas.supertaster.viewmodels

import androidx.lifecycle.ViewModel
import com.carvajal.lucas.supertaster.data.Recipe

class DashboardViewModel : ViewModel() {

    fun getGreeting() : String {
        //TODO
        return "Good Morning"
    }

    fun getSuggestions(): List<Recipe> {
        //TODO
        return listOf(
            Recipe(id = 0, title = "Smash Burger", "Burger", "BBQ", 4, 5, 5),
            Recipe(id = 0, title = "Lasagne", "Italian", "Lunch", 6, 25, 45),
            Recipe(id = 0, title = "Peking Duck", "Chinese", "Dinner", 4, 45, 105),
            Recipe(id = 0, title = "Tacos al Pastor ", "Mexican", "Snack", 2, 25, 15)
        )
    }
}