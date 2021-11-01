package com.carvajal.lucas.supertaster.viewmodels

import androidx.lifecycle.ViewModel
import com.carvajal.lucas.supertaster.data.Cookbook

class CookbookViewModel : ViewModel() {

    fun getCookbooks(): List<Cookbook> {
        //TODO
        return listOf(Cookbook(0, "Burgers"))
    }

}