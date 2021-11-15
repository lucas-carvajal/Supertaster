package com.carvajal.lucas.supertaster.viewmodels

import androidx.lifecycle.ViewModel
import com.carvajal.lucas.supertaster.data.AppRepository
import com.carvajal.lucas.supertaster.data.Recipe

class DashboardViewModel(private val repository: AppRepository) : ViewModel() {

    fun getGreeting() : String {
        //TODO
        return "GOOD MORNING"
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

    fun getSampleRecipes(): List<Recipe> {


        //TODO
        return listOf(
            Recipe(id = 0, title = "Stuffed Paprika", "Mediterranean", "Lunch", 4, 5, 5),
            Recipe(id = 0, title = "Burritos", "Mexican", "Lunch", 6, 25, 45),
            Recipe(id = 0, title = "Noodle Soup with Chicken", "Chinese", "Lunch", 4, 45, 105),
            Recipe(id = 0, title = "Chicken in Lemon Coconut Sauce ", "Sri Lankan", "Dinner", 2, 25, 15),
            Recipe(id = 0, title = "Swedish Meatballs ", "Swedish", "Dinner", 2, 25, 15)
        )
    }
}