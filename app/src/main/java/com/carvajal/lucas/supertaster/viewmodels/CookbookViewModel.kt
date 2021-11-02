package com.carvajal.lucas.supertaster.viewmodels

import androidx.lifecycle.ViewModel
import com.carvajal.lucas.supertaster.data.Cookbook

class CookbookViewModel : ViewModel() {

    fun getCookbooks(): List<Cookbook> {
        //TODO
//        return emptyList()
        return listOf(
            Cookbook(0, "Burgers"),
            Cookbook(1, "Thai"),
            Cookbook(2, "Soups"),
            Cookbook(3, "BBQ"),
            Cookbook(4, "French"),
            Cookbook(5, "One Pot"),
            Cookbook(6, "Mediterranean"),
            Cookbook(7, "Steak"),
            Cookbook(8, "Noodles"),
            Cookbook(9, "Pastries"),
            Cookbook(9, "Sushi")
        )
    }

}